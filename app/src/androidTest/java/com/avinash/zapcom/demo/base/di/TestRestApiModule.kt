package com.avinash.zapcom.demo.base.di

import com.avinash.zapcom.demo.di.RestModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@TestInstallIn(
    components = [SingletonComponent::class, ],
    replaces = [RestModule::class]
)
@Module
class TestRestApiModule: RestModule() {
    override fun baseURL() = LOCAL_HOST_URL

    companion object {
        const val LOCAL_HOST_URL = "https://localhost:8000"
    }
}