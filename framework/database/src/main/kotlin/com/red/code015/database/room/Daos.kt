package com.red.code015.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg characterEntity: CharacterEntity)

    @Query("SELECT * FROM character_table WHERE page_id = :pageId")
    suspend fun getByPageId(pageId: Int): List<CharacterEntity>

}

@Dao
interface PageInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg pageInfoEntity: PageInfoEntity)

    @Query("SELECT * FROM page_info_table WHERE page_info_id = :id")
    suspend fun getById(id: Int): PageInfoEntity?

}

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg episodeEntity: EpisodeEntity)

    @Query("SELECT * FROM episode_table WHERE episode_id = :id")
    suspend fun getById(id: Int): EpisodeEntity?

}