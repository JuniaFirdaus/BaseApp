package com.junfirdaus.disneyhotstar.core.domain.usecase

import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getAllTourism(): Flow<Resource<List<Tourism>>>
    fun getFavoriteTourism(): Flow<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}