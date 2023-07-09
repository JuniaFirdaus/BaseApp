package com.junfirdaus.disneyhotstar.core.di

import androidx.room.Room
import com.junfirdaus.disneyhotstar.core.BuildConfig
import com.junfirdaus.disneyhotstar.core.data.AppRepository
import com.junfirdaus.disneyhotstar.core.data.source.local.LocalDataSource
import com.junfirdaus.disneyhotstar.core.data.source.local.room.AppDatabase
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
    factory { get<AppDatabase>().appDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "Movies.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.APP_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            .connectTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
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
            get()
        )
    }
}