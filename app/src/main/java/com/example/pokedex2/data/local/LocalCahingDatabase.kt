package com.example.pokedex2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalCaching::class], version = 1, exportSchema = false)
abstract class LocalCachingDatabase : RoomDatabase() {
    abstract fun localCachingDao(): LocalCachingDao
}
