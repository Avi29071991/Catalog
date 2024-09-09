package com.avinash.zapcom.demo.catalog

import com.avinash.zapcom.demo.base.BaseViewModelState
import com.avinash.zapcom.demo.base.BaseViewState
import com.avinash.zapcom.demo.model.CatalogItem

sealed interface CatalogViewState: BaseViewState {
    val isLoading: Boolean
    val isError: Boolean
    val isInternetError: Boolean

    data class DisplayCatalog(
        val list: List<CatalogItem>
    ): CatalogViewState {
        override val isLoading: Boolean
            get() = false
        override val isError: Boolean
            get() = false
        override val isInternetError: Boolean
            get() = false
    }

    data object DisplayProgress: CatalogViewState {
        override val isLoading: Boolean
            get() = true
        override val isError: Boolean
            get() = false
        override val isInternetError: Boolean
            get() = false
    }

    data object DisplayNetworkError: CatalogViewState {
        override val isLoading: Boolean
            get() = false
        override val isError: Boolean
            get() = false
        override val isInternetError: Boolean
            get() = true
    }

    data object DisplayApiError: CatalogViewState {
        override val isLoading: Boolean
            get() = false
        override val isError: Boolean
            get() = true
        override val isInternetError: Boolean
            get() = false
    }
}

data class CatalogViewModelState(
    val isLoading: Boolean,
    val isInternetError: Boolean,
    val isError: Boolean,
    val list: List<CatalogItem>? = null
): BaseViewModelState<CatalogViewState>{
    override fun toUIState(): CatalogViewState {
        return if (isLoading) {
            CatalogViewState.DisplayProgress
        } else {
            if (isInternetError) {
                CatalogViewState.DisplayNetworkError
            } else {
                if (!isError && !list.isNullOrEmpty()) {
                    CatalogViewState.DisplayCatalog(list)
                } else {
                    CatalogViewState.DisplayApiError
                }
            }
        }
    }
}

