package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers

import com.google.gson.annotations.SerializedName

data class AuthorDetails(

	@field:SerializedName("avatar_path")
	val avatarPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)