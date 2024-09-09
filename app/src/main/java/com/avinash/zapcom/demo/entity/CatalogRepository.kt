package com.avinash.zapcom.demo.entity

import com.avinash.zapcom.demo.model.CatalogItem
import io.reactivex.rxjava3.core.Observable

interface CatalogRepository {
    fun fetchCatalog(): Observable<RestApiResponse<List<CatalogItem>>>
}

abstract class RestApiResponse<T>(val message: String?, val response: T?)
class RestApiOKResponse<T>(result: T?) : RestApiResponse<T>("OK", result)
class RestApiFailedResponse<T>(message: String?) : RestApiResponse<T>(message, null)
class RestApiNetworkError<T>(message: String): RestApiResponse<T>(message, null)