package com.example.pokedex2.data.local

import android.content.Context

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


suspend fun saveFavouritePokemon(context: Context, pokemon: com.example.pokedex.Pokemon) {
    context.favouritesDataStore.updateData { currentFavourites ->
        currentFavourites.toBuilder()
            .addFavourites(pokemon)
            .build()
    }
}


fun getFavouritePokemons(context: Context): Flow<MutableList<com.example.pokedex.Pokemon>> {
    return context.favouritesDataStore.data
        .map { favourites -> favourites.favouritesList }
}

suspend fun removeFavouritePokemon(context: Context, pokemonId: Int) {
    context.favouritesDataStore.updateData { currentFavourites ->
        val updatedFavourites = currentFavourites.favouritesList.filter { it.id != pokemonId }
        currentFavourites.toBuilder()
            .clearFavourites()
            .addAllFavourites(updatedFavourites)
            .build()
    }
}

