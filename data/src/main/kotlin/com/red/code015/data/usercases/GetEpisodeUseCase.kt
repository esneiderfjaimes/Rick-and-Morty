package com.red.code015.data.usercases

import com.red.code015.data.Repository
import kotlinx.coroutines.flow.Flow
import rickandmorty.domain.models.PageCharacters
import javax.inject.Inject

internal typealias GetPageCharactersBaseUseCase = BaseUseCase<Int, Flow<PageCharacters>>

class GetPageCharactersUseCase @Inject constructor(
    private val repository: Repository,
) : GetPageCharactersBaseUseCase {

    override fun invoke(params: Int) = repository.getPageCharacters(params)

}