package com.red.code015.api.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/")
    suspend fun getPageCharacters(@Query("page") page: Int): PageCharactersResponseServer

    @GET("episode/{episodeId}")
    suspend fun getEpisode(@Path("episodeId") id: Int): EpisodeResponseServer

}
