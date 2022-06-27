package com.junfirdaus.disneyhotstar.core.domain.usecase

import com.junfirdaus.disneyhotstar.core.domain.model.Tourism
import com.junfirdaus.disneyhotstar.core.domain.repository.IAppRepository

class AppInteractor(private val tourismRepository: IAppRepository): AppUseCase {
    override fun getAllTourism() = tourismRepository.getAllTourism()

    override fun getFavoriteTourism() = tourismRepository.getFavoriteTourism()

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) = tourismRepository.setFavoriteTourism(tourism, state)
}