package com.avinash.zapcom.demo.catalog

import androidx.lifecycle.viewModelScope
import com.avinash.zapcom.demo.base.BaseViewModel
import com.avinash.zapcom.demo.base.domain.PostExecutionThread
import com.avinash.zapcom.demo.base.domain.ThreadExecutor
import com.avinash.zapcom.demo.domain.FetchCatalogUseCase
import com.avinash.zapcom.demo.entity.RestApiFailedResponse
import com.avinash.zapcom.demo.entity.RestApiNetworkError
import com.avinash.zapcom.demo.entity.RestApiOKResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
    private val useCase: FetchCatalogUseCase
): BaseViewModel<CatalogViewState, CatalogViewModelState>() {

    override var mutableStateFlow: MutableStateFlow<CatalogViewModelState> =
        MutableStateFlow(CatalogViewModelState(isLoading = false, isError = false, isInternetError = false))

    override var uiState = mutableStateFlow
        .map(CatalogViewModelState::toUIState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            mutableStateFlow.value.toUIState()
        )

    fun fetchCatalogList(isInternetConnected: Boolean) {
        if (!isInternetConnected) {
            mutableStateFlow.update {
                it.copy(isLoading = false, isInternetError = true)
            }
            return
        }

        mutableStateFlow.update {
            it.copy(isLoading = true, isInternetError = false)
        }

        compositeDisposables.add(
            useCase.execute()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler())
                .subscribe({ result ->
                      when(result) {
                          is RestApiOKResponse -> {
                              mutableStateFlow.update {
                                  it.copy(isLoading = false, isInternetError = false, isError = false, list = result.response)
                              }
                          }
                          is RestApiNetworkError -> {
                              mutableStateFlow.update {
                                  it.copy(isLoading = false, isInternetError = true, isError = false)
                              }
                          }
                          is RestApiFailedResponse -> {
                              mutableStateFlow.update {
                                  it.copy(isLoading = false, isInternetError = false, isError = true)
                              }
                          }
                      }
                }, {
                    mutableStateFlow.update {
                        it.copy(isLoading = false, isInternetError = false, isError = true)
                    }
                })
        )
    }
}