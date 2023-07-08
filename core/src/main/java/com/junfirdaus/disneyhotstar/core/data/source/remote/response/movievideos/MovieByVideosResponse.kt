package com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos

import com.google.gson.annotations.SerializedName

data class MovieByVideosResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideosItem>
)