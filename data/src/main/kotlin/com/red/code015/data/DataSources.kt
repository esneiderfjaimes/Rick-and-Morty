package com.red.code015.data

import rickandmorty.domain.models.PageCharacters

interface RemoteDataSource {
    suspend fun getPageCharacters(page: Int): PageCharacters
}
