package com.junfirdaus.disneyhotstar.core.data.source.local.room

import androidx.room.*
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.TourismEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM tourism")
    fun getAllTourism(): Flow<List<TourismEntity>>

    @Query("SELECT * FROM tourism where isFavorite = 1")
    fun getFavoriteTourism(): Flow<List<TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourism: List<TourismEntity>)

    @Update
    fun updateFavoriteTourism(tourism: TourismEntity)

    //Genre
    @Query("SELECT * FROM genre")
    fun getGenres(): Flow<List<GenreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: List<GenreEntity>)

    //Movies
    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MoviesEntity>)
}