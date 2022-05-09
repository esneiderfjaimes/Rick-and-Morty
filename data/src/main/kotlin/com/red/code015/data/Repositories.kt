package com.red.code015.data

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
) {

    suspend fun getPageCharacters(page: Int) = flow {
        emit(remote.getPageCharacters(page))
    }

}
