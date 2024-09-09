package com.avinash.zapcom.demo.base.domain

import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseUseCase<T : Any> {
    private var disposable: Disposable? = null

    protected abstract fun build(params: Any? = null): T

    fun subscribe(disposable: Disposable) {
        this.disposable = disposable
    }

    fun unsubscribe() {
        disposable?.run {
            this.dispose()
        }
    }
}
