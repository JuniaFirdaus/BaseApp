package com.junfirdaus.disneyhotstar.core.data.source.local

import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.room.AppDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val appDao: AppDao) {

    //Genre
    fun getGenres(): Flow<List<GenreEntity>> = appDao.getGenres()
    suspend fun insertGenre(genre: List<GenreEntity>) = appDao.insertGenre(genre)

    //Movies
    fun getMovies(): Flow<List<MoviesEntity>> = appDao.getMovies()
    suspend fun insertMovies(movies: List<MoviesEntity>) = appDao.insertMovies(movies)
}