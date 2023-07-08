package com.junfirdaus.disneyhotstar.core.data

import androidx.paging.PagingData
import com.junfirdaus.disneyhotstar.core.data.source.local.LocalDataSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.RemoteDataSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.TourismResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.genre.GenresItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid.MovieByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviewbyid.MovieReviewByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar.MoviesSimilarItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos.VideosItem
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel
import com.junfirdaus.disneyhotstar.core.domain.model.Tourism
import com.junfirdaus.disneyhotstar.core.domain.repository.IAppRepository
import com.junfirdaus.disneyhotstar.core.utils.AppExecutors
import com.junfirdaus.disneyhotstar.core.utils.DataMapper
import com.junfirdaus.disneyhotstar.core.utils.GenreMapper
import com.junfirdaus.disneyhotstar.core.utils.MoviesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AppRepository(
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

    override fun getGenre(): Flow<Resource<List<GenresModel>>> =
        object : NetworkBoundResource<List<GenresModel>, List<GenresItem>>() {
            override fun loadFromDB(): Flow<List<GenresModel>> {
                return localDataSource.getGenres().map {
                    GenreMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GenresModel>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GenresItem>>> =
                remoteDataSource.getGenres()

            override suspend fun saveCallResult(data: List<GenresItem>) {
                val genreList = GenreMapper.mapResponsesToEntities(data)
                localDataSource.insertGenre(genreList)
            }
        }.asFlow()

    override fun getMovies(page: Int, genre: Int): Flow<Resource<List<MoviesModel>>> =
        object : NetworkBoundResource<List<MoviesModel>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<MoviesModel>> {
                return localDataSource.getMovies().map {
                    MoviesMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MoviesModel>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getMovies(page, genre)

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = MoviesMapper.mapResponsesToEntities(genre, data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getMovieById(id: String): Flow<Resource<MovieByIdResponse>> {
        return flow {
            emit(Resource.Loading())
            when (val res = remoteDataSource.getMovieById(id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getMovieVideos(id: String): Flow<Resource<List<VideosItem>>> {
        return flow {
            emit(Resource.Loading())
            when (val res = remoteDataSource.getMovieVideos(id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getMovieReviewers(
        key: String
    ): Flow<Resource<PagingData<ReviewersItem>>> {
        return flow {
            emit(Resource.Loading())
            when (val res =
                remoteDataSource.getMovieReviewers(key).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }
    }


    override fun getMovieReviewById(id: String): Flow<Resource<MovieReviewByIdResponse>> {
        return flow {
            emit(Resource.Loading())
            when (val res = remoteDataSource.getMovieReviewById(id).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getMoviesSimilar(key: String): Flow<Resource<PagingData<MoviesSimilarItem>>> {
        return flow {
            emit(Resource.Loading())
            when (val res =
                remoteDataSource.getMoviesSimilar(key).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }
    }

    override fun getMoviesByGenre(genreId: Int): Flow<Resource<PagingData<MoviesItem>>> {
        return flow {
            emit(Resource.Loading())
            when (val res =
                remoteDataSource.getMoviesByGenre(genreId).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(res.data))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(res.errorMessage))
                }
                else -> {}
            }
        }

    }
}