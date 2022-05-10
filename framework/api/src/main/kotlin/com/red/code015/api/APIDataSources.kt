package com.red.code015.api

import com.red.code015.api.retrofit.RickAndMortyService
import com.red.code015.data.RemoteDataSource
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.PageCharacters

class RetrofitDataSource(private val service: RickAndMortyService) :
    RemoteDataSource {

    override suspend fun getPageCharacters(page: Int): PageCharacters =
        service.getPageCharacters(page).toDomain()

    override suspend fun getEpisode(id: Int): Episode =
        service.getEpisode(id).toDomain()

}
