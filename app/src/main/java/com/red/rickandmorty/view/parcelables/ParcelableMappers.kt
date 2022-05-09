package com.red.rickandmorty.view.parcelables

import rickandmorty.domain.models.Character
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.Location

fun Character.toParcelable() = CharacterParcelable(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toParcelable(),
    location = location.toParcelable(),
    image = image,
    episodes = episodesIds.associateWith { null },
)

fun Location.toParcelable() = LocationParcelable(id, name)

fun Episode.toParcelable() = EpisodeParcelable(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    charactersIds = charactersIds
)