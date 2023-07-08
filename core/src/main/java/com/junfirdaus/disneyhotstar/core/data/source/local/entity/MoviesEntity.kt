package com.junfirdaus.disneyhotstar.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesEntity(

	@ColumnInfo(name = "overview")
	val overview: String,

	@ColumnInfo(name = "genre")
	val genre: Int,

	@ColumnInfo(name = "original_language")
	val originalLanguage: String,

	@ColumnInfo(name = "original_title")
	val originalTitle: String,

	@ColumnInfo(name = "video")
	val video: Boolean,

	@ColumnInfo(name = "title")
	val title: String,
	
	@ColumnInfo(name = "poster_path")
	val posterPath: String,

	@ColumnInfo(name = "backdrop_path")
	val backdropPath: String,

	@ColumnInfo(name = "release_date")
	val releaseDate: String,

	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,

	@ColumnInfo(name = "id_movie")
	val idMovie: Int,
)