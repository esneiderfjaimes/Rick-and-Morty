package com.red.code015.data.usercases

internal interface BaseUseCase<in Parameter, out Result> {
    operator fun invoke(params: Parameter): Result
}

