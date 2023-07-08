package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers

import com.google.gson.annotations.SerializedName

data class MovieReviewersResponse(

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<ReviewersItem>,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)