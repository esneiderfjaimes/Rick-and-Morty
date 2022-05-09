package com.red.code015.api

import android.net.Uri
import com.red.code015.api.retrofit.*
import rickandmorty.domain.models.*

internal fun PageCharactersResponseServer.toDomain() = PageCharacters(
    info = info?.toDomain()!!,
    results = results?.map { it.toDomain() }!!
)

internal fun InfoResponseServer.toDomain() = Info(
    count = count!!,
    pages = pages!!,
    next = getUriParameterOr(next, "page") { toInt() },
    prev = getUriParameterOr(prev, "page") { toInt() },
)

internal fun CharacterResponseServer.toDomain() = Character(
    id = id!!,
    name = name!!,
    status = status!!,
    species = species!!,
    type = type!!,
    gender = gender!!,
    origin = origin?.toDomain()!!,
    location = location?.toDomain()!!,
    image = image!!,
    episodesIds = episode!!.mapNotNull { getUriLastPathOr(it) { toInt() } },
)

internal fun LocationResponseServer.toDomain() =
    Location(getUriLastPathOr(url, 0) { toInt() }!!, name!!)

internal fun EpisodeResponseServer.toDomain() = Episode(
    id = id!!,
    name = name!!,
    airDate = airDate!!,
    episode = episode!!,
    charactersIds = characters!!.mapNotNull { getUriLastPathOr(it) { toInt() } },
)

fun <T> getUriParameterOr(
    url: String?,
    key: String,
    orValue: T? = null,
    convert: String.() -> T?,
): T? = try {
    val uri: Uri = Uri.parse(url!!)
    convert(uri.getQueryParameter(key)!!)
} catch (e: Exception) {
    orValue
}

fun <T> getUriLastPathOr(
    url: String?,
    orValue: T? = null,
    convert: String.() -> T,
): T? = try {
    val uri: Uri = Uri.parse(url!!)
    val last = uri.pathSegments.last()
    convert(last!!)
} catch (e: Exception) {
    orValue
}
