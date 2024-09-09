package com.avinash.zapcom.demo.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.avinash.zapcom.demo.R
import com.avinash.zapcom.demo.compose.AppText
import com.avinash.zapcom.demo.compose.BannerImage
import com.avinash.zapcom.demo.compose.ErrorScreen
import com.avinash.zapcom.demo.compose.FullScreenLoading
import com.avinash.zapcom.demo.compose.HorizontalSpacer
import com.avinash.zapcom.demo.compose.SplitBannerImage
import com.avinash.zapcom.demo.compose.TileImage
import com.avinash.zapcom.demo.model.CatalogItem
import com.avinash.zapcom.demo.model.ProductItem
import com.avinash.zapcom.demo.model.SectionType

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    list: List<CatalogItem>
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .testTag(CatalogViewTags.MainScreen.MAIN_SCROLLABLE_SCREEN.name)
    ) {
        HorizontalSpacer()

        list.forEachIndexed { index, catalogItem ->
            val productList = catalogItem.items
            if (!productList.isNullOrEmpty()) {
                when(catalogItem.catalogSectionType()) {
                    SectionType.HORIZONTAL_SCROLL -> {
                        AppText(
                            modifier = Modifier
                                .wrapContentSize()
                                .testTag("${CatalogViewTags.HorizontalScroll.HS_TITLE.name}_$index"),
                            value = stringResource(id = R.string.horizontal_catalog),
                            colorValue = R.color.black,
                            dimenValue = 18,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalSpacer()
                        HorizontalFreeScrollCatalog(items = catalogItem.items, index = index)
                        repeat(2) { HorizontalSpacer() }
                    }
                    SectionType.BANNER -> {
                        AppText(
                            modifier = Modifier.wrapContentSize().testTag("${CatalogViewTags.Banner.B_TITLE.name}_$index"),
                            value = stringResource(id = R.string.banner_catalog),
                            colorValue = R.color.black,
                            dimenValue = 18,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalSpacer()
                        productList.forEachIndexed { pIndex, productItem ->
                            BannerItem(item = productItem, index = index, productIndex = pIndex)
                        }
                        HorizontalSpacer()
                    }
                    SectionType.SPLIT_BANNER -> {
                        AppText(
                            modifier = Modifier.wrapContentSize().testTag("${CatalogViewTags.SplitBanner.SB_TITLE.name}_$index"),
                            value = stringResource(id = R.string.split_banner_catalog),
                            colorValue = R.color.black,
                            dimenValue = 18,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalSpacer()
                        SplitBannerCatalog(items = productList, index = index)
                        repeat(2) { HorizontalSpacer() }
                    }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun BannerItem(item: ProductItem, index: Int, productIndex: Int) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        BannerImage(
            modifier = Modifier.testTag("${CatalogViewTags.Banner.B_IMAGE.name}_${index}_${productIndex}"),
            item = item
        )

        val name = item.name
        if (!name.isNullOrBlank()) {
            AppText(
                modifier = Modifier.wrapContentSize().testTag("${CatalogViewTags.Banner.B_IMAGE_TITLE.name}_${index}_${productIndex}"),
                value = name,
                colorValue = R.color.black,
                dimenValue = 14,
                fontWeight = FontWeight.Light
            )
        }

        HorizontalSpacer()
    }
}


@Composable
fun HorizontalFreeScrollCatalog(
    items: List<ProductItem>,
    index: Int
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()).testTag("${CatalogViewTags.HorizontalScroll.HS_ROW}_${index}")
    ) {
        items.forEachIndexed { pIndex, productItem ->
            HorizontalItem(item = productItem, index = index, productIndex = pIndex)
        }
    }
}

@Composable
fun SplitBannerCatalog(items: List<ProductItem>, index: Int) {
    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { pIndex, item ->
            Column {
                SplitBannerImage(
                    modifier = Modifier.testTag("${CatalogViewTags.SplitBanner.SB_IMAGE.name}_${index}_${pIndex}"),
                    item = item
                )
                val name = item.name
                if (!name.isNullOrBlank()) {
                    AppText(
                        modifier = Modifier.wrapContentSize().testTag("${CatalogViewTags.SplitBanner.SB_IMAGE_TITLE.name}_${index}_${pIndex}"),
                        value = name,
                        colorValue = R.color.black,
                        dimenValue = 14,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalItem(
    item: ProductItem,
    index: Int,
    productIndex: Int
) {
    Column (
      modifier = Modifier
          .wrapContentSize()
    ) {
        TileImage(
            modifier = Modifier.testTag("${CatalogViewTags.HorizontalScroll.HS_IMAGE.name}_${index}_${productIndex}"),
            item = item
        )

        val name = item.name
        if (!name.isNullOrBlank()) {
            AppText(
                modifier = Modifier
                    .wrapContentSize()
                    .testTag("${CatalogViewTags.HorizontalScroll.HS_IMAGE_TITLE.name}_${index}_${productIndex}"),
                value = name,
                colorValue = R.color.black,
                dimenValue = 14,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier
) {
    val topBarAppState = rememberTopAppBarState()
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(topBarAppState)
    TopAppBar(
        title = {
            AppText(
                modifier = modifier
                    .testTag(CatalogViewTags.Toolbar.TOOLBAR_TITLE.name),
                value = stringResource(id = R.string.app_bar_title),
                colorValue = R.color.purple_700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                dimenValue = 24,
                fontWeight = FontWeight.Bold
            )
        },
        scrollBehavior = scrollBehaviour,
        modifier = modifier.background(colorResource(id = R.color.white)),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.white))
    )
}


@Composable
fun CatalogView(
    uiState: CatalogViewState,
    modifier: Modifier = Modifier,
    onRefreshButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                AppTopBar(
                    modifier = modifier
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when(uiState) {
            is CatalogViewState.DisplayProgress -> { FullScreenLoading(modifier = contentModifier) }
            is CatalogViewState.DisplayApiError -> {
                ErrorScreen(modifier = contentModifier, message = stringResource(id = R.string.api_error), onClick = onRefreshButtonClicked)
            }
            is CatalogViewState.DisplayNetworkError -> {
                ErrorScreen(modifier = contentModifier, message = stringResource(id = R.string.internet_error), onClick = onRefreshButtonClicked)
            }
            is CatalogViewState.DisplayCatalog -> {
                CatalogScreen(modifier = contentModifier, list = uiState.list)
            }
        }
    }
}

object CatalogViewTags {
    enum class MainScreen {
        MAIN_SCROLLABLE_SCREEN
    }

    enum class Toolbar {
        TOOLBAR_TITLE
    }

    enum class Banner {
        B_TITLE,
        B_IMAGE,
        B_IMAGE_TITLE
    }

    enum class SplitBanner {
        SB_TITLE,
        SB_IMAGE,
        SB_IMAGE_TITLE
    }

    enum class HorizontalScroll {
        HS_ROW,
        HS_TITLE,
        HS_IMAGE,
        HS_IMAGE_TITLE
    }
}