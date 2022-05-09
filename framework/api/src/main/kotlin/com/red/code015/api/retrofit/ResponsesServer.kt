package com.red.code015.api.retrofit

import com.google.gson.annotations.SerializedName

data class PageCharactersResponseServer(
    @SerializedName("info") var info: InfoResponseServer?,
    @SerializedName("results") var results: List<CharacterResponseServer>?,
)

data class InfoResponseServer(
    @SerializedName("count") var count: Int?,
    @SerializedName("pages") var pages: Int?,
    @SerializedName("next") var next: String?,
    @SerializedName("prev") var prev: String?,
)

data class CharacterResponseServer(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("species") var species: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("gender") var gender: String?,
    @SerializedName("origin") var origin: LocationResponseServer?,
    @SerializedName("location") var location: LocationResponseServer?,
    @SerializedName("image") var image: String?,
    @SerializedName("episode") var episode: List<String>?,
    @SerializedName("url") var url: String?,
    @SerializedName("created") var created: String?,
)

data class LocationResponseServer(
    @SerializedName("name") var name: String?,
    @SerializedName("url") var url: String?,
)

data class EpisodeResponseServer(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("air_date") var airDate: String?,
    @SerializedName("episode") var episode: String?,
    @SerializedName("characters") var characters: List<String>?,
    @SerializedName("url") var url: String?,
    @SerializedName("created") var created: String?,
)