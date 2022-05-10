package com.red.code015.database.di

import android.content.Context
import com.red.code015.data.LocalDataSource
import com.red.code015.database.RoomDataSource
import com.red.code015.database.room.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context)
            : RickAndMortyDatabase = RickAndMortyDatabase.getDatabase(context)

    @Provides
    fun localDataSourceProvider(
        database: RickAndMortyDatabase,
    ): LocalDataSource = RoomDataSource(database)

}
