package com.junfirdaus.disneyhotstar.core.utils

import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel

object MoviesMapper {
    fun mapResponsesToEntities(genre: Int, input: List<MoviesItem>): List<MoviesEntity> {
        val moviesList = ArrayList<MoviesEntity>()
        input.map {
            val movie = MoviesEntity(
                overview = it.overview ?: "",
                genre = genre,
                originalLanguage = it.originalLanguage ?: "",
                originalTitle = it.originalTitle ?: "",
                video = it.video ?: false,
                title = it.title ?: "",
                posterPath = it.posterPath ?: "",
                backdropPath = it.backdropPath ?: "",
                releaseDate = it.releaseDate ?: "",
                idMovie = it.id ?: 0,
            )
            moviesList.add(movie)
        }
        return moviesList
    }

    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<MoviesModel> =
        input.map {
            MoviesModel(
                overview = it.overview,
                genre = it.genre,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                idMovie = it.idMovie,
                id = it.id
            )
        }
}