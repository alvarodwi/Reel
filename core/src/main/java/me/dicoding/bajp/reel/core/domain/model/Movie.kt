package me.dicoding.bajp.reel.core.domain.model

data class Movie(
    val id: Long,
    val poster: String?,
    val backdrop: String?,
    val releaseDate: String,

    val title: String,
    val tagLine: String,
    val overview: String,
    val genres: List<String>,
    val homepage: String?,
) {
    val posterUrl = String.format("https://image.tmdb.org/t/p/w500%s", poster)
    val backdropUrl = String.format("https://image.tmdb.org/t/p/w500%s", backdrop)

    val tmdbUrl = String.format("https://www.themoviedb.org/movie/%d", id)
}
