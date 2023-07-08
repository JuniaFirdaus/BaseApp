package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar

import com.google.gson.annotations.SerializedName

data class MoviesSimilarResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<MoviesSimilarItem>,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)