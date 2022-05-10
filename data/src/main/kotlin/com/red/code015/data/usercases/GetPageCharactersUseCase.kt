package com.red.code015.data.usercases

import com.red.code015.data.Repository
import kotlinx.coroutines.flow.Flow
import rickandmorty.domain.models.Episode
import rickandmorty.domain.models.Result
import javax.inject.Inject

internal typealias GetEpisodeBaseUseCase = BaseUseCase<IntArray, Flow<Result<Episode>>>

class GetEpisodeUseCase @Inject constructor(
    private val repository: Repository,
) : GetEpisodeBaseUseCase {

    override fun invoke(params: IntArray) = repository.getEpisodes(*params)

}