package com.avinash.zapcom.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avinash.zapcom.demo.base.BaseActivity
import com.avinash.zapcom.demo.catalog.CatalogView
import com.avinash.zapcom.demo.catalog.CatalogViewModel
import com.avinash.zapcom.demo.catalog.CatalogViewModelState
import com.avinash.zapcom.demo.catalog.CatalogViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogActivity: BaseActivity<CatalogViewState, CatalogViewModelState, CatalogViewModel>() {
    override val viewModel: CatalogViewModel by viewModels()

    @Composable
    override fun composeContent() {
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        CatalogView(
            uiState = uiState.value,
            onRefreshButtonClicked = { viewModel.fetchCatalogList(isInternetConnected()) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchCatalogList(isInternetConnected())
    }
}