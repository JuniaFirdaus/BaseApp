package com.junfirdaus.disneyhotstar.core.domain.repository

import androidx.paging.PagingData
import com.junfirdaus.disneyhotstar.core.data.Resource
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid.MovieByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviewbyid.MovieReviewByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.ReviewersItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar.MoviesSimilarItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos.VideosItem
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.nowplaying.NowPlayingsItem
import com.junfirdaus.disneyhotstar.core.domain.model.GenresModel
import com.junfirdaus.disneyhotstar.core.domain.model.MoviesModel
import kotlinx.coroutines.flow.Flow

interface IAppRepository {

    fun getGenre(): Flow<Resource<List<GenresModel>>>

    fun getMovies(page: Int, genre: Int): Flow<Resource<List<MoviesModel>>>

    fun getMovieById(id: String): Flow<Resource<MovieByIdResponse>>

    fun getMovieVideos(id: String): Flow<Resource<List<VideosItem>>>

    fun getMovieReviewers(key: String): Flow<Resource<PagingData<ReviewersItem>>>

    fun getMovieReviewById(id: String): Flow<Resource<MovieReviewByIdResponse>>

    fun getMoviesSimilar(key: String): Flow<Resource<PagingData<MoviesSimilarItem>>>

    fun getMoviesByGenre(genreId: Int): Flow<Resource<PagingData<MoviesItem>>>

    fun getNowPlayingMovies(page: Int): Flow<Resource<List<NowPlayingsItem>>>
}