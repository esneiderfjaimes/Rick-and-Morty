package com.red.code015.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class, PageInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun pageInfoDao(): PageInfoDao

    abstract fun episodesDao(): EpisodesDao

    companion object {

        private const val DATABASE_NAME = "rick_and_morty_database"

        @Synchronized
        fun getDatabase(context: Context): RickAndMortyDatabase = Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}
