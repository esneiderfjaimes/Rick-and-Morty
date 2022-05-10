package com.red.code015.data

import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.PageCharacters

interface RemoteDataSource {
    suspend fun getPageCharacters(page: Int): PageCharacters
    suspend fun getEpisode(id: Int): Episode
}
