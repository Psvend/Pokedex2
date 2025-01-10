package com.example.pokedex2.data.local

import android.content.Context
import com.example.pokedex.FavouriteAffirmation
import com.example.pokedex2.model.Affirmation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
// Save a favourite affirmation
suspend fun saveFavouritePokemon(context: Context, affirmation: Affirmation) {
    context.favouritesDataStore.updateData { currentFavourites ->
        currentFavourites.toBuilder()
            .addFavourites(affirmation.toProto())
            .build()
    }
}

// Convert `Affirmation` to its proto representation
private fun Affirmation.toProto(): FavouriteAffirmation {
    return FavouriteAffirmation.newBuilder()
        .setId(id)
        .setName(name)
        .setImageResourceId(imageResourceId)
        .addAllTypeIcon(typeIcon)
        .setIsLiked(isLiked)
        .setNumber(number)
        .build()
}

// Retrieve all favourite affirmations
fun getFavouriteAffirmations(context: Context): Flow<List<Affirmation>> {
    return context.favouritesDataStore.data
        .map { favourites ->
            favourites.favouritesList.map { it.toAffirmation() }
        }
}

// Convert `FavouriteAffirmation` proto back to `Affirmation`
private fun FavouriteAffirmation.toAffirmation(): Affirmation {
    return Affirmation(
        id = id,
        name = name,
        imageResourceId = imageResourceId,
        typeIcon = typeIconList,
        isLiked = isLiked,
        number = number
    )
}

// Remove a favourite affirmation by ID
suspend fun removeFavouriteAffirmation(context: Context, affirmationId: Int) {
    context.favouritesDataStore.updateData { currentFavourites ->
        val updatedFavourites = currentFavourites.favouritesList.filter { it.id != affirmationId }
        currentFavourites.toBuilder()
            .clearFavourites()
            .addAllFavourites(updatedFavourites)
            .build()
    }
}

 */
