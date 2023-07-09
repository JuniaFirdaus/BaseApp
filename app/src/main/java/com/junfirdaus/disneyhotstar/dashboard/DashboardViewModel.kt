package com.junfirdaus.disneyhotstar.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid.MovieByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar.MoviesSimilarItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos.VideosItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.nowplaying.NowPlayingsItem
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel
import com.junfirdaus.disneyhotstar.core.domain.usecase.AppUseCase
import kotlinx.coroutines.flow.Flow

class DashboardViewModel(private val appUseCase: AppUseCase) : ViewModel() {

    fun getGenres(
    ): LiveData<Resource<List<GenresModel>>> {
        return appUseCase.getGenres().asLiveData()
    }

    fun getMovies(
        genre: Int
    ): LiveData<Resource<List<MoviesModel>>> {
        return appUseCase.getMovies(1, genre).asLiveData()
    }

    fun getMovieById(
        id: String
    ): LiveData<Resource<MovieByIdResponse>> {
        return appUseCase.getMovieById(id).asLiveData()
    }

    fun getMoviesSimilar(id: String): Flow<Resource<PagingData<MoviesSimilarItem>>> {
        return appUseCase.getMoviesSimilar(id)
    }

    fun getMovieVideos(
        id: String
    ): LiveData<Resource<List<VideosItem>>> {
        return appUseCase.getMovieVideos(id).asLiveData()
    }

    fun getMovieReviewers(
        id: String
    ): Flow<Resource<PagingData<ReviewersItem>>> {
        return appUseCase.getMovieReviewers(id)
    }

    fun getMoviesByGenre(genreId: Int): Flow<Resource<PagingData<MoviesItem>>> {
        return appUseCase.getMoviesByGenre(genreId)
    }

    fun getNowPlayingMovies(): LiveData<Resource<List<NowPlayingsItem>>> {
        return appUseCase.getNowPlayingMovies(1).asLiveData()
    }

}