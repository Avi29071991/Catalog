package com.avinash.zapcom.demo.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface BaseViewState

interface BaseViewModelState<T : BaseViewState> {
    fun toUIState(): T
}

abstract class BaseViewModel<
        VS : BaseViewState,
        VMS : BaseViewModelState<VS>
        > : ViewModel() {

    open lateinit var mutableStateFlow: MutableStateFlow<VMS>
    open val compositeDisposables = CompositeDisposable()
    open lateinit var uiState: StateFlow<VS>

    protected fun updateState(closure: (VMS) -> VMS) {
        mutableStateFlow.update {
            closure.invoke(it)
        }
    }

    override fun onCleared() {
        if (compositeDisposables.isDisposed) {
            compositeDisposables.clear()
        }
        super.onCleared()
    }
}