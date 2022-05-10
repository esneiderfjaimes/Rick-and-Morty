package com.red.code015.database.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id") var id: Int,
    @ColumnInfo(name = "character_name") var name: String,
    @ColumnInfo(name = "character_status") var status: String,
    @ColumnInfo(name = "character_species") var species: String,
    @ColumnInfo(name = "character_type") var type: String,
    @ColumnInfo(name = "character_gender") var gender: String,
    @Embedded(prefix = "origin_") var origin: LocationEntity,
    @Embedded(prefix = "current_") var location: LocationEntity,
    @ColumnInfo(name = "character_image") var image: String,
    @ColumnInfo(name = "character_episodes_ids") var episodesIds: List<Int>,
    @ColumnInfo(name = "page_id") var pageId: Int,
)

data class LocationEntity(
    @ColumnInfo(name = "location_id") var id: Int,
    @ColumnInfo(name = "location_name") var name: String,
)

@Entity(tableName = "page_info_table")
data class PageInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "page_info_id") var id: Int,
    @ColumnInfo(name = "page_info_count") var count: Int,
    @ColumnInfo(name = "page_info_pages") var pages: Int,
    @ColumnInfo(name = "page_info_next") var next: Int?,
    @ColumnInfo(name = "page_info_prev") var prev: Int?,
)

@Entity(tableName = "episode_table")
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "episode_id") var id: Int,
    @ColumnInfo(name = "episode_name") var name: String,
    @ColumnInfo(name = "episode_air_date") var airDate: String,
    @ColumnInfo(name = "episode_episode") var episode: String,
    @ColumnInfo(name = "episode_characters") var charactersIds: List<Int>,
)
