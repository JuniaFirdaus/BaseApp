package com.junfirdaus.disneyhotstar.core.data.source.local

import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.TourismEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.room.AppDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val appDao: AppDao) {

    fun getAllTourism(): Flow<List<TourismEntity>> = appDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<TourismEntity>> = appDao.getFavoriteTourism()

    suspend fun insertTourism(tourismList: List<TourismEntity>) = appDao.insertTourism(tourismList)

    fun setFavoriteTourism(tourism: TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        appDao.updateFavoriteTourism(tourism)
    }

    //Genre
    fun getGenres(): Flow<List<GenreEntity>> = appDao.getGenres()
    suspend fun insertGenre(genre: List<GenreEntity>) = appDao.insertGenre(genre)

    //Movies
    fun getMovies(): Flow<List<MoviesEntity>> = appDao.getMovies()
    suspend fun insertMovies(movies: List<MoviesEntity>) = appDao.insertMovies(movies)
}