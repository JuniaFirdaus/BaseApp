package com.junfirdaus.disneyhotstar.core.data.source.remote.network

import com.junfirdaus.disneyhotstar.core.data.source.remote.response.genre.GenreMoviesResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebygenre.MoviesByGenreResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviebyid.MovieByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviewbyid.MovieReviewByIdResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviereviwers.MovieReviewersResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.moviessimilar.MoviesSimilarResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.movievideos.MovieByVideosResponse
import com.junfirdaus.disneyhotstar.core.data.source.remote.response.nowplaying.NowPlayingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreMoviesResponse

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("with_genres") genre: Int,
    ): MoviesByGenreResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: String,
    ): MovieByIdResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") id: String,
    ): MovieByVideosResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewers(
        @Path("movie_id") id: String,
        @Query("page") page: Int,
    ): MovieReviewersResponse

    @GET("movie/{review_id}/reviews")
    suspend fun getMovieReviewById(
        @Path("review_id") id: String,
    ): MovieReviewByIdResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Path("movie_id") id: String,
        @Query("page") page: Int,
    ): MoviesSimilarResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): NowPlayingResponse
}