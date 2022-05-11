package com.red.code015.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.PageCharacters
import rickandmorty.domain.models.Result
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) {

    /**
     * returns the schema of a page of characters according to their id,
     * the database [local] is consulted first and
     * if it is not available the api [remote] is tried
     *
     * @param pageId page identifier
     * @return query flow
     */
    fun getPageCharacters(pageId: Int) = flow {
        val requiresPreload = local.isEmptyCharacters()

        var pageCharacters: PageCharacters?
        val time = measureTimeMillis {

            local.getPageCharacters(pageId)?.let {
                pageCharacters = it
                return@measureTimeMillis
            }

            remote.getPageCharacters(pageId).let {
                local.insertPageCharacters(it)
                pageCharacters = it
                return@measureTimeMillis
            }
        }

        pageCharacters?.let { emit(it) }
        Log.i("Rep", "getPageCharacters($pageId): time:$time")

        if (requiresPreload)
            pageCharacters?.info?.next?.let { tryPreload(it) }

    }.flowOn(Dispatchers.IO)

    private suspend fun tryPreload(pageId: Int = 1) {
        val next: Int?
        val time = measureTimeMillis {
            try {
                remote.getPageCharacters(pageId).let {
                    local.insertPageCharacters(it)
                    next = it.info.next
                }
            } catch (e: Exception) {
                Log.e("Rep", "findAndSave($pageId): e:${e.message}")
                return
            }
        }
        Log.i("Rep", "findAndSave($pageId): time:$time")

        // Keep trying if possible
        next?.let { tryPreload(it) }
    }

    /**
     * loop through the list of identifiers and issue an episode result
     *
     * @param ids episode identifiers
     * @return query flow
     */
    fun getEpisodes(vararg ids: Int) = flow {

        delay(500)
        ids.forEach { episodeId ->
            val time = measureTimeMillis {
                emit(getEpisode(episodeId))
            }
            Log.i("Rep", "getEpisode($episodeId): time:$time")
        }

        emit(Result.Finish)
    }.flowOn(Dispatchers.IO)

    /**
     * returns episode result according to their id,
     * the database [local] is consulted first and
     * if it is not available the api [remote] is tried
     * if they fail an failure result is returned
     *
     * @param episodeId - episode identifiers
     * @return  episode result
     */
    private suspend fun getEpisode(episodeId: Int): Result<Episode> {
        // try querying the database
        local.getEpisode(episodeId)?.let {
            delay(50)
            Result.Success(it)
        }

        return try { // if it fails try to query the api
            return remote.getEpisode(episodeId).let {
                // insert into database for future queries
                local.insertEpisode(it)
                Result.Success(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // return the value, if it cannot be obtained by any datasource it generates an exception
            Result.Failure(
                Throwable("information cannot be obtained")
            )
        }
    }

}
