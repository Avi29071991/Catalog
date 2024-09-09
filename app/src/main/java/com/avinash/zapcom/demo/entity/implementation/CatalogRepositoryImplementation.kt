package com.avinash.zapcom.demo.entity.implementation

import android.annotation.SuppressLint
import com.avinash.zapcom.demo.entity.CatalogRepository
import com.avinash.zapcom.demo.entity.RestApiFailedResponse
import com.avinash.zapcom.demo.entity.RestApiOKResponse
import com.avinash.zapcom.demo.entity.RestApiResponse
import com.avinash.zapcom.demo.model.CatalogItem
import com.avinash.zapcom.demo.retrofit.CatalogService
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import javax.inject.Inject

class CatalogRepositoryImplementation @Inject constructor(
    private val api: CatalogService
): CatalogRepository {

    override fun fetchCatalog(): Observable<RestApiResponse<List<CatalogItem>>> {
        return validateObservableWithError(api.fetchCatalog())
    }

    @SuppressLint("CheckResult")
    private fun <T> validateObservableWithError(
        observable: Observable<Response<T>>
    ): Observable<RestApiResponse<T>> {
        return observable
            .doOnError {
                Observable.just(RestApiFailedResponse<T>(message = "observable error"))
            }
            .switchMap {
                processResponse(response = it)
            }
    }

    private fun <T> processResponse(
        response: Response<T>
    ): Observable<RestApiResponse<T>> {
        val value = when {
            SUCCESS_RANGE.contains(response.code()) -> {
                when (response.code()) {
                    SUCCESS_NO_CONTENT_CODE -> { RestApiOKResponse<T>(null) }
                    else -> {
                        if (response.body() != null) {
                            RestApiOKResponse(response.body())
                        } else {
                            RestApiOKResponse<T>(null)
                        }
                    }
                }
            }
            else -> {
                RestApiFailedResponse(message = "Error received while fetching response")
            }
        }

        return Observable.just(value)
    }

    companion object {
        private val SUCCESS_RANGE = 200..300
        private const val SUCCESS_NO_CONTENT_CODE = 204
    }
}