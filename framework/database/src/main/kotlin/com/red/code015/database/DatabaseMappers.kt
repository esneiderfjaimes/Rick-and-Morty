package com.red.code015.database

import com.red.code015.database.room.CharacterEntity
import com.red.code015.database.room.EpisodeEntity
import com.red.code015.database.room.LocationEntity
import com.red.code015.database.room.PageInfoEntity
import rickandmorty.domain.models.Character
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.Info
import rickandmorty.domain.models.Location

internal fun Character.toEntity(pageId: Int) = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toEntity(),
    location = location.toEntity(),
    image = image,
    episodesIds = episodesIds,
    pageId = pageId
)

internal fun Location.toEntity() = LocationEntity(id, name)

internal fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDomain(),
    location = location.toDomain(),
    image = image,
    episodesIds = episodesIds,
)

internal fun LocationEntity.toDomain() = Location(id, name)

internal fun Episode.toEntity() = EpisodeEntity(id, name, airDate, episode, charactersIds)

internal fun EpisodeEntity.toDomain() = Episode(id, name, airDate, episode, charactersIds)

internal fun Info.toEntity(id: Int) = PageInfoEntity(id, count, pages, next, prev)

internal fun PageInfoEntity.toDomain() = Info(count, pages, next, prev)