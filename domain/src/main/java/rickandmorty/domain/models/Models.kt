package rickandmorty.domain.models

/**
 * Character schema
 */
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episodesIds: List<Int>,
)

/**
 * Location schema
 */
data class Location(
    val id: Int,
    val name: String,
)

/**
 * Episode schema
 */
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val charactersIds: List<Int>,
)