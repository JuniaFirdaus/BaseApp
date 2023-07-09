package com.junfirdaus.disneyhotstar.core.domain.usecase

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
import com.junfirdaus.disneyhotstar.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow

class AppInteractor(private val iAppRepository: IAppRepository) : AppUseCase {

    override fun getGenres(): Flow<Resource<List<GenresModel>>> = iAppRepository.getGenre()

    override fun getMovies(page: Int, genre: Int): Flow<Resource<List<MoviesModel>>> =
        iAppRepository.getMovies(page, genre)

    override fun getMovieById(id: String): Flow<Resource<MovieByIdResponse>> =
        iAppRepository.getMovieById(id)

    override fun getMovieVideos(id: String): Flow<Resource<List<VideosItem>>> =
        iAppRepository.getMovieVideos(id)

    override fun getMovieReviewers(id: String): Flow<Resource<PagingData<ReviewersItem>>> =
        iAppRepository.getMovieReviewers(id)

    override fun getMovieReviewById(id: String): Flow<Resource<MovieReviewByIdResponse>> =
        iAppRepository.getMovieReviewById(id)

    override fun getMoviesSimilar(id: String): Flow<Resource<PagingData<MoviesSimilarItem>>> =
        iAppRepository.getMoviesSimilar(id)

    override fun getMoviesByGenre(genreId: Int): Flow<Resource<PagingData<MoviesItem>>> =
        iAppRepository.getMoviesByGenre(genreId)

    override fun getNowPlayingMovies(page: Int): Flow<Resource<List<NowPlayingsItem>>> =
        iAppRepository.getNowPlayingMovies(page)
}