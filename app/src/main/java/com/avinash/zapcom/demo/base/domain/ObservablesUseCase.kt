package com.avinash.zapcom.demo.base.domain

import io.reactivex.rxjava3.core.Observable

abstract class ObservablesUseCase<T : Any>: BaseUseCase<Observable<T>>() {

    fun execute(params: Any? = null): Observable<T> {
        return build(params = params)
    }
}
