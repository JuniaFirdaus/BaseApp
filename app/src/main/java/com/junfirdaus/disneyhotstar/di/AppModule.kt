package com.junfirdaus.disneyhotstar.di

import com.junfirdaus.disneyhotstar.core.domain.usecase.AppInteractor
import com.junfirdaus.disneyhotstar.core.domain.usecase.AppUseCase
import com.junfirdaus.disneyhotstar.dashboard.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AppUseCase> { AppInteractor(get()) }
}


val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
}