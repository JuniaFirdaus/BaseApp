package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviewbyid

import com.google.gson.annotations.SerializedName

data class MovieReviewByIdResponse(

	@field:SerializedName("media_title")
	val mediaTitle: String? = null,

	@field:SerializedName("author_details")
	val authorDetails: AuthorDetails? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("media_type")
	val mediaType: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("media_id")
	val mediaId: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)