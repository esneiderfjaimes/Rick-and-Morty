package com.red.rickandmorty.view.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterParcelable(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationParcelable,
    val location: LocationParcelable,
    val image: String?,
    val episodes: Map<Int, EpisodeParcelable?>,
) : Parcelable

@Parcelize
data class LocationParcelable(
    val id: Int,
    val name: String,
) : Parcelable

/**
 * Episode schema
 */
@Parcelize
data class EpisodeParcelable(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val charactersIds: List<Int>,
) : Parcelable