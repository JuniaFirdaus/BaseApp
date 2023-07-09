package com.junfirdaus.disneyhotstar.core.data.source.local.room

import androidx.room.*
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

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