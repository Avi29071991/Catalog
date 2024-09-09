package com.avinash.zapcom.demo.domain

import com.avinash.zapcom.demo.base.domain.ObservablesUseCase
import com.avinash.zapcom.demo.entity.CatalogRepository
import com.avinash.zapcom.demo.entity.RestApiResponse
import com.avinash.zapcom.demo.model.CatalogItem
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FetchCatalogUseCase @Inject constructor(
    private val repository: CatalogRepository
): ObservablesUseCase<RestApiResponse<List<CatalogItem>>>() {
    override fun build(params: Any?): Observable<RestApiResponse<List<CatalogItem>>> {
        return repository.fetchCatalog()
    }
}