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
    val image: String?,
    val episodeList: ArrayList<String>,
    val url: String,
    val created: String,
)

/**
 * Location schema
 */
data class Location(
    val name: String,
    val url: String,
)

/**
 * Episode schema
 */
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
)