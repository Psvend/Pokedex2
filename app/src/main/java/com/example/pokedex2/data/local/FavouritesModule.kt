package com.example.pokedex2.data.local

import android.content.Context
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.data.local.FavouritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // scope for context
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}

@Module
@InstallIn(SingletonComponent::class) // Available at the app level
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFavouritesRepository(
        @ApplicationContext context: Context
    ): FavouritesRepository {
        return FavouritesRepositoryImpl(context)
    }
}


