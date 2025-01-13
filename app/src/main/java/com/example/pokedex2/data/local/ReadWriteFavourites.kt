package com.example.pokedex2.data.local

import android.content.Context
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.proto.FavouriteAffirmation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


suspend fun saveFavouritePokemon(context: Context, affirmation: Affirmation) {
    context.favouritesDataStore.updateData { currentFavourites ->
        currentFavourites.toBuilder()
            .addAllFavourites(currentFavourites.favouritesList + affirmation.toProto())
            .build()
    }
}

private fun Affirmation.toProto(): FavouriteAffirmation {
    return FavouriteAffirmation.newBuilder()
        .setId(id)
        .setName(name)
        .setImageResourceId(imageResourceId)
        .addAllTypeIcon(typeIcon) // Add all type icons
        .setIsLiked(isLiked)
        .setNumber(number)
        .build()
}


fun getFavouriteAffirmations(context: Context): Flow<List<Affirmation>> {
    return context.favouritesDataStore.data.map { currentFavourites ->
        currentFavourites.favouritesList.map { it.toAffirmation() }
    }
}


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


suspend fun removeFavouriteAffirmation(context: Context, affirmationId: Int) {
    context.favouritesDataStore.updateData { currentFavourites ->
        val updatedFavourites = currentFavourites.favouritesList.filter { it.id != affirmationId }
        currentFavourites.toBuilder()
            .clearFavourites() // Clear the old list
            .addAllFavourites(updatedFavourites) // Add updated list
            .build()
    }
}


