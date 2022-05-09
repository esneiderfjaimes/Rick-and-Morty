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