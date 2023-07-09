package com.junfirdaus.disneyhotstar.core.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.network.ApiService
import com.junfirdaus.disneyhotstar.core.data.source.remote.paging.MovieReviewersPagingSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.paging.MoviesByGenrePagingSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.paging.MoviesSimilarPagingSource
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.genre.GenresItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid.MovieByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviewbyid.MovieReviewByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar.MoviesSimilarItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos.VideosItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.nowplaying.NowPlayingsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getGenres(): Flow<ApiResponse<List<GenresItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getGenres()
                val dataArray = response.genres
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.genres))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovies(page: Int, genre: Int): Flow<ApiResponse<List<MoviesItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovies(page = page, genre = genre)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieById(id: String): Flow<ApiResponse<MovieByIdResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovieById(id = id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieVideos(id: String): Flow<ApiResponse<List<VideosItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovieVideos(id = id)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieReviewers(
        key: String,
    ): Flow<ApiResponse<PagingData<ReviewersItem>>> {
        return flow {
            try {
                val response = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        MovieReviewersPagingSource(key, apiService)
                    }
                ).flow
                emit(ApiResponse.Success(response.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieReviewById(id: String): Flow<ApiResponse<MovieReviewByIdResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovieReviewById(id = id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMoviesSimilar(id: String): Flow<ApiResponse<PagingData<MoviesSimilarItem>>> {
        return flow {
            try {
                val response = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        MoviesSimilarPagingSource(id, apiService)
                    }
                ).flow
                emit(ApiResponse.Success(response.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMoviesByGenre(genre: Int): Flow<ApiResponse<PagingData<MoviesItem>>> {
        return flow {
            try {
                val response = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = {
                        MoviesByGenrePagingSource(genre, apiService)
                    }
                ).flow
                emit(ApiResponse.Success(response.first()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNowPlayingMovies(id: Int): Flow<ApiResponse<List<NowPlayingsItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getNowPlayingMovies(page = id)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}