package com.junfirdaus.disneyhotstar.core.di

import android.icu.util.TimeUnit
import androidx.room.Room
import com.junfirdaus.disneyhotstar.core.data.AppRepository
import com.junfirdaus.disneyhotstar.core.data.source.local.LocalDataSource
import com.junfirdaus.disneyhotstar.core.data.source.local.room.TourismDatabase
import com.junfirdaus.disneyhotstar.core.data.source.remote.RemoteDataSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiService
import com.junfirdaus.disneyhotstar.core.domain.repository.IAppRepository
import com.junfirdaus.disneyhotstar.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<TourismDatabase>().tourismDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            TourismDatabase::class.java, "Tourism.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tourism-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IAppRepository> {
        AppRepository(
            get(),
            get(),
            get()
        )
    }
}