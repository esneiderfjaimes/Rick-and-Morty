package com.red.code015.data

import kotlinx.coroutines.flow.flow
import rickandmorty.domain.models.Result
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
) {

    suspend fun getPageCharacters(page: Int) = flow {
        emit(remote.getPageCharacters(page))
    }

    fun getEpisodes(vararg ids: Int) = flow {

        ids.forEach { episodeId ->
            try {
                val episode = remote.getEpisode(episodeId)
                emit(Result.Success(episode))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }

        emit(Result.Finish)
    }

}
