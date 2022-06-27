package com.junfirdaus.disneyhotstar.core.domain.repository

import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism(): Flow<List<Tourism>>

    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}