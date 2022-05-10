package rickandmorty.domain.models

data class PageCharacters(
    var info: Info,
    var results: List<Character>,
)

data class Info(
    var count: Int,
    var pages: Int,
    var next: Int?,
    var prev: Int?,
) {
    val currentPage: Int = when {
        next != null -> next!! - 1
        prev != null -> prev!! + 1
        else -> 0
    }
}

/**
 * A generic class that contains a value with the possible response from the repository.
 * @param T value type
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    object Finish : Result<Nothing>()
}
