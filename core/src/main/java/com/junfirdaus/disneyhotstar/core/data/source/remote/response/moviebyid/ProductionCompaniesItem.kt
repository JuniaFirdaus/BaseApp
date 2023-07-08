package com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
)