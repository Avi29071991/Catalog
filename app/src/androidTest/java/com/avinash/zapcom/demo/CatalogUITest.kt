package com.avinash.zapcom.demo

import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avinash.zapcom.demo.base.BaseComposeTest
import com.avinash.zapcom.demo.catalog.CatalogView
import com.avinash.zapcom.demo.catalog.CatalogViewModel
import com.avinash.zapcom.demo.catalog.CatalogViewTags
import com.avinash.zapcom.demo.compose.GenericViewTags
import com.avinash.zapcom.demo.domain.FetchCatalogUseCase
import com.avinash.zapcom.demo.entity.implementation.CatalogRepositoryImplementation
import com.avinash.zapcom.demo.retrofit.CatalogService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy

@RunWith(AndroidJUnit4::class)
class CatalogUITest: BaseComposeTest() {

    @get:Rule(order = 0)
    val composeRule = createAndroidComposeRule<HiltTestingActivity>()

    private lateinit var viewModel: CatalogViewModel

    @Spy
    private lateinit var useCase: FetchCatalogUseCase

    override fun setUp() {
        super.setUp()
        val catalogService =
            buildMockRetrofit().create(CatalogService::class.java)
        useCase =
            FetchCatalogUseCase(repository = CatalogRepositoryImplementation(catalogService))
        viewModel = CatalogViewModel(threadExecutor, postExecutionThread, useCase)
        MainScope().launch {
            composeRule.activity.apply {
                setContent {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    CatalogView(
                        uiState = uiState,
                        onRefreshButtonClicked = {
                            viewModel.fetchCatalogList(isInternetConnected = true)
                        }
                    )
                }
            }
        }
    }

    @Test
    fun testWithRetryScenario() {
        enqueueMockResponse("error.json", 400)
        viewModel.fetchCatalogList(isInternetConnected = true)
        performTask {
            with(composeRule) {
                onNodeWithTag(GenericViewTags.Error.ERROR_VIEW.name)
                    .assertIsDisplayed()

                onNodeWithTag(GenericViewTags.Error.ERROR_TITLE.name)
                    .assertIsDisplayed()
                    .assert(hasText("Unable to fetch catalog. Please try again"))

                onNodeWithTag(GenericViewTags.Error.ERROR_BUTTON.name, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .onChild()
                    .assertIsDisplayed()
                    .assert(hasText("Retry") and hasTestTag(GenericViewTags.Error.ERROR_BUTTON_TEXT.name))

                enqueueMockResponse("success.json", 200)

                onNodeWithTag(GenericViewTags.Error.ERROR_BUTTON.name)
                    .performClick()

                performTask {
                    successfulDisplayAssertion()
                }
            }
        }
    }

    @Test
    fun testInternetErrorScenario() {
        enqueueMockResponse("success.json", 200)

        viewModel.fetchCatalogList(isInternetConnected = false)
        performTask {
            with(composeRule) {
                onNodeWithTag(GenericViewTags.Error.ERROR_VIEW.name)
                    .assertIsDisplayed()

                onNodeWithTag(GenericViewTags.Error.ERROR_TITLE.name)
                    .assertIsDisplayed()
                    .assert(hasText("Internet connection is not available. Please try again"))

                onNodeWithTag(GenericViewTags.Error.ERROR_BUTTON.name, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .onChild()
                    .assertIsDisplayed()
                    .assert(hasText("Retry") and hasTestTag(GenericViewTags.Error.ERROR_BUTTON_TEXT.name))
            }
        }
    }

    private fun successfulDisplayAssertion() {
        with(composeRule) {
            onNodeWithTag("${CatalogViewTags.Banner.B_TITLE.name}_0")
                .assertIsDisplayed()
                .assert(hasText("Banner catalog"))

            onNodeWithTag("${CatalogViewTags.Banner.B_IMAGE.name}_0_0")
                .assertIsDisplayed()

            onNodeWithTag("${CatalogViewTags.Banner.B_IMAGE_TITLE.name}_0_0")
                .assertIsDisplayed()
                .assert(hasText("Bag pack"))

            onNodeWithTag("${CatalogViewTags.HorizontalScroll.HS_TITLE.name}_1")
                .assertIsDisplayed()
                .assert(hasText("Horizontal Catalog"))

            testHorizontalItem(0, "Laptop")
            testHorizontalItem(1, "Hat")
            testHorizontalItem(2, "Sunglasses")

            onNodeWithTag("${CatalogViewTags.HorizontalScroll.HS_ROW.name}_1")
                .performScrollToNode(hasTestTag("${CatalogViewTags.HorizontalScroll.HS_IMAGE.name}_1_4"))

            performTask {
                testHorizontalItem(3, "Watch")
                testHorizontalItem(4, "Phone")

                onNodeWithTag(CatalogViewTags.MainScreen.MAIN_SCROLLABLE_SCREEN.name)
                    .performScrollToNode(hasTestTag("${CatalogViewTags.SplitBanner.SB_IMAGE_TITLE.name}_2_0"))

                performTask {
                    onNodeWithTag("${CatalogViewTags.SplitBanner.SB_TITLE.name}_2")
                        .assertIsDisplayed()
                        .assert(hasText("Split Banner catalog"))

                    testSplitBanner(pIndex = 0, productName = "Perfumes")
                    testSplitBanner(pIndex = 1, productName = "Camera")
                }
            }
        }
    }

    private fun testSplitBanner(
        pIndex: Int,
        productName: String
    ) {
        with(composeRule) {
            onNodeWithTag("${CatalogViewTags.SplitBanner.SB_IMAGE.name}_2_${pIndex}")
                .assertIsDisplayed()

            onNodeWithTag("${CatalogViewTags.SplitBanner.SB_IMAGE_TITLE.name}_2_${pIndex}")
                .assertIsDisplayed()
                .assert(hasText(productName))
        }
    }

    private fun testHorizontalItem(
        pIndex: Int,
        productName: String
    ) {
        with(composeRule) {
            onNodeWithTag("${CatalogViewTags.HorizontalScroll.HS_IMAGE.name}_1_${pIndex}")
                .assertIsDisplayed()

            onNodeWithTag("${CatalogViewTags.HorizontalScroll.HS_IMAGE_TITLE.name}_1_${pIndex}")
                .assertIsDisplayed()
                .assert(hasText(productName))
        }
    }
}