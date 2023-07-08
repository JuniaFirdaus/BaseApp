package com.junfirdaus.disneyhotstar.core.domain.model

data class MoviesModel(

    val overview: String,
    val genre: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val idMovie: Int,
    val id: Int,
)