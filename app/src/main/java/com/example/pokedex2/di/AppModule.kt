package com.example.pokedex2.di


import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Database
import androidx.room.Room
import com.example.pokedex2.data.local.PokemonDao
import com.example.pokedex2.data.local.PokemonDatabase
import com.example.pokedex2.data.local.PokemonEntity
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.PokemonRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }
    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApiService {
        return Retrofit.Builder()
            .baseUrl(PokemonApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun providePokemonPager(pokemonDB: PokemonDatabase, pokemonApi: PokemonApiService): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(
                database = pokemonDB,
                pokemonApi = pokemonApi
            ),
            pagingSourceFactory = {
                pokemonDB.pokemonDao().pagingSource()
            }
        )

    }
}