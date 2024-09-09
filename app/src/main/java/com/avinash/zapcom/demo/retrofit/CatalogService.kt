package com.avinash.zapcom.demo.retrofit

import com.avinash.zapcom.demo.model.CatalogItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface CatalogService {

    @GET("b/5BEJ")
    fun fetchCatalog(): Observable<Response<List<CatalogItem>>>
}