package com.avinash.zapcom.demo.di

import com.avinash.zapcom.demo.base.domain.JobExecutor
import com.avinash.zapcom.demo.base.domain.PostExecutionThread
import com.avinash.zapcom.demo.base.domain.ThreadExecutor
import com.avinash.zapcom.demo.base.domain.UIThread
import com.avinash.zapcom.demo.entity.CatalogRepository
import com.avinash.zapcom.demo.entity.implementation.CatalogRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsCatalogRepository(repository: CatalogRepositoryImplementation): CatalogRepository

    companion object {
        @Provides
        @Singleton
        fun providesThreadExecutor(): ThreadExecutor {
            return JobExecutor()
        }

        @Provides
        @Singleton
        fun providesPostExecutionThread(): PostExecutionThread {
            return UIThread()
        }
    }
}