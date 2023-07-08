package com.junfirdaus.disneyhotstar.core.utils

import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.genre.GenresItem
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel

object GenreMapper {
    fun mapResponsesToEntities(input: List<GenresItem>): List<GenreEntity> {
        val genreList = ArrayList<GenreEntity>()
        input.map {
            val genre = GenreEntity(name = it.name ?: "", id = it.id ?: 0)
            genreList.add(genre)
        }
        return genreList
    }

    fun mapEntitiesToDomain(input: List<GenreEntity>): List<GenresModel> =
        input.map {
            GenresModel(name = it.name, id = it.id)
        }
}