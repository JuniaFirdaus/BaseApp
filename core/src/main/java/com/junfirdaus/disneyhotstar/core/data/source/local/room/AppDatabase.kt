package com.junfirdaus.disneyhotstar.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.GenreEntity
import com.junfirdaus.disneyhotstar.core.data.source.local.entity.MoviesEntity

@Database(
    entities = [GenreEntity::class, MoviesEntity::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}