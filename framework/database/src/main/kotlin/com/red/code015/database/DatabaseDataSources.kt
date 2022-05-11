package com.red.code015.database

import com.red.code015.data.LocalDataSource
import com.red.code015.database.room.RickAndMortyDatabase
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.PageCharacters

class RoomDataSource(
    database: RickAndMortyDatabase,
) : LocalDataSource {

    private val charactersDao by lazy { database.charactersDao() }
    private val episodesDao by lazy { database.episodesDao() }
    private val pageInfoDao by lazy { database.pageInfoDao() }

    override suspend fun isEmptyCharacters(): Boolean {
        return charactersDao.first() == null
    }

    override suspend fun insertPageCharacters(pageCharacters: PageCharacters) {

        val characterEntityList = pageCharacters.run {
            results.map { it.toEntity(info.currentPage) }
        }

        val pageInfoEntity = pageCharacters.info.toEntity(pageCharacters.info.currentPage)

        charactersDao.insert(*characterEntityList.toTypedArray())
        pageInfoDao.insert(pageInfoEntity)
    }

    override suspend fun getPageCharacters(page: Int): PageCharacters? {
        val characterList = charactersDao
            .getByPageId(page)
            .map { it.toDomain() }

        if (characterList.isEmpty()) return null

        val info = pageInfoDao.getById(page)?.toDomain()

        return info?.let {
            PageCharacters(
                info = it,
                results = characterList
            )
        }
    }

    override suspend fun insertEpisode(episode: Episode) {
        episodesDao.insert(episode.toEntity())
    }

    override suspend fun getEpisode(id: Int): Episode? {
        return episodesDao.getById(id)?.toDomain()
    }

}
