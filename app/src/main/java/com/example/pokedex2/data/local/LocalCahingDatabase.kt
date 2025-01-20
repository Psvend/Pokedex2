package com.example.pokedex2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [LocalCaching::class], version = 1, exportSchema = false)
@TypeConverters(BooleanConverters::class)
abstract class LocalCachingDatabase : RoomDatabase() {
    abstract fun localCachingDao(): LocalCachingDao
}
