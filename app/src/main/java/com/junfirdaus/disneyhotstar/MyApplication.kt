package com.junfirdaus.disneyhotstar

import android.app.Application
import com.junfirdaus.disneyhotstar.core.di.databaseModule
import com.junfirdaus.disneyhotstar.core.di.networkModule
import com.junfirdaus.disneyhotstar.core.di.repositoryModule
import com.junfirdaus.disneyhotstar.di.useCaseModule
import com.junfirdaus.disneyhotstar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}