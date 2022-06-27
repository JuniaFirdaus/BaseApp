package com.junfirdaus.disneyhotstar.core.data

import com.junfirdaus.disneyhotstar.core.data.source.local.LocalDataSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.RemoteDataSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.TourismResponse
import com.junfirdaus.disneyhotstar.core.domain.model.Tourism
import com.junfirdaus.disneyhotstar.core.domain.repository.IAppRepository
import com.junfirdaus.disneyhotstar.core.utils.AppExecutors
import com.junfirdaus.disneyhotstar.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppRepository {
    override fun getAllTourism(): Flow<Resource<List<Tourism>>> =
        object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
            override fun loadFromDB(): Flow<List<Tourism>> {
                return localDataSource.getAllTourism().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Tourism>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            override suspend fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
            }
        }.asFlow()

    override fun getFavoriteTourism(): Flow<List<Tourism>> {
        return localDataSource.getFavoriteTourism().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
    }
}