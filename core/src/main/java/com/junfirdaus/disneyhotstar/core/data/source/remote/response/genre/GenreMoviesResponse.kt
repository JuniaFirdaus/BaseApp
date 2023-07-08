package com.junfirdaus.disneyhotstar.core.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

data class GenreMoviesResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
)