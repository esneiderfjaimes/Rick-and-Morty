package com.red.code015.api.di

import com.red.code015.api.RetrofitDataSource
import com.red.code015.api.retrofit.RickAndMortyRequest
import com.red.code015.api.retrofit.RickAndMortyService
import com.red.code015.data.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = "https://rickandmortyapi.com/api/"

    @Provides
    fun serviceProvider(
        @Named("baseUrl") baseUrl: String,
    ): RickAndMortyService = RickAndMortyRequest(baseUrl).getService()

    // Level data

    @Provides
    fun remoteDataSourceProvider(
        rickAndMortyService: RickAndMortyService,
    ): RemoteDataSource = RetrofitDataSource(rickAndMortyService)

}

