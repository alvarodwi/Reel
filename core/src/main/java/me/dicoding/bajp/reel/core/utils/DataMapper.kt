package me.dicoding.bajp.reel.core.utils

import me.dicoding.bajp.reel.core.data.db.entity.FavoriteEntity
import me.dicoding.bajp.reel.core.data.network.json.FavoriteJson
import me.dicoding.bajp.reel.core.data.network.json.MovieJson
import me.dicoding.bajp.reel.core.data.network.json.TvShowJson
import me.dicoding.bajp.reel.core.domain.model.Favorite
import me.dicoding.bajp.reel.core.domain.model.Movie
import me.dicoding.bajp.reel.core.domain.model.TvShow
import me.dicoding.bajp.reel.core.utils.DatabaseConstants.FavoriteTable.Types
import java.time.LocalDateTime

//map db entity as domain
fun FavoriteEntity.asDomain(): Favorite = Favorite(
  uid = this.uid,
  tmdbId = this.tmdbId,
  itemTitle = this.itemTitle,
  itemDate = this.itemDate,
  itemPosterUrl = this.itemPosterUrl,
  dateAdded = this.dateAdded,
  type = this.type
)

//map response to domain
fun MovieJson.asDomain(): Movie {
  val listGenre: List<String> = genres.map { it.name }
  return Movie(
    id = id,
    poster = poster,
    backdrop = backdrop,
    releaseDate = releaseDate,
    title = title,
    overview = overview,
    tagLine = tagLine ?: "",
    genres = listGenre,
    homepage = homepage,
  )
}

fun TvShowJson.asDomain(): TvShow {
  val listGenre: List<String> = genres.map { it.name }
  return TvShow(
    id = id,
    poster = poster,
    backdrop = backdrop,
    firstAirDate = firstAirDate,
    name = name,
    overview = overview,
    genres = listGenre,
    homepage = homepage,
  )
}

fun FavoriteJson.asDomain() = Favorite(
  uid = this.uid,
  tmdbId = this.tmdbId,
  itemTitle = this.itemTitle,
  itemDate = this.itemDate,
  itemPosterUrl = this.itemPosterUrl,
  dateAdded = this.dateAdded,
  type = this.type
)

//map to favorite
fun Movie.asFavoriteEntity() = FavoriteEntity(
  tmdbId = this.id,
  itemTitle = this.title,
  itemPosterUrl = this.posterUrl,
  itemDate = this.releaseDate,
  dateAdded = LocalDateTime.now().toString(),
  type = Types.TYPE_MOVIE
)

fun TvShow.asFavoriteEntity() = FavoriteEntity(
  tmdbId = this.id,
  itemTitle = this.name,
  itemPosterUrl = this.posterUrl,
  itemDate = this.firstAirDate,
  dateAdded = LocalDateTime.now().toString(),
  type = Types.TYPE_TV_SHOW
)