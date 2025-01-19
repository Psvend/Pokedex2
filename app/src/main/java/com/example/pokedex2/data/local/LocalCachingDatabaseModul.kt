package com.example.pokedex2.data.local

import android.content.Context
import androidx.room.Room
import com.example.pokedex2.data.local.LocalCachingDao
import com.example.pokedex2.data.local.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalCachingDatabase(context: Context): LocalCachingDatabase {
        return Room.databaseBuilder(
            context,
            LocalCachingDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    @Provides
    fun provideLocalCachingDao(localCachingDatabase: LocalCachingDatabase): LocalCachingDao {
        return localCachingDatabase.localCachingDao()
    }
}
