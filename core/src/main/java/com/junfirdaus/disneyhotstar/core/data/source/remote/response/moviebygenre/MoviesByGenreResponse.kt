package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre

import com.google.gson.annotations.SerializedName

data class MoviesByGenreResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<MoviesItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)