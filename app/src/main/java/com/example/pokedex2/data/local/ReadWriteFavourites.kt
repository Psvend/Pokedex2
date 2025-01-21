package com.example.pokedex2.data.local

import android.content.Context
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.proto.FavouriteAffirmation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.pokedex2.proto.Stat



suspend fun saveFavouritePokemon(context: Context, affirmation: Affirmation) {
    context.favouritesDataStore.updateData { currentFavourites ->
        // Remove any existing entry with the same ID
        val updatedFavourites = currentFavourites.favouritesList
            .filter { it.id != affirmation.id }
            .toMutableList()

        updatedFavourites.add(affirmation.toProto())

        currentFavourites.toBuilder()
            .clearFavourites()
            .addAllFavourites(updatedFavourites)
            .build()
    }
}


private fun Affirmation.toProto(): FavouriteAffirmation {
    return FavouriteAffirmation.newBuilder()
        .setId(id)
        .setName(name)
        .setImageResourceId(imageResourceId)
        .addAllTypeIcon(typeIcon)
        .setIsLiked(isLiked)
        .setNumber(number)
        .addAllAbility(ability)
        .addAllHeldItem(heldItem)
        .addAllStats(stats.toProto())
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
        number = number,
        ability = abilityList,
        heldItem = heldItemList,
        stats = statsList.toDomain()
    )
}


suspend fun removeFavouriteAffirmation(context: Context, affirmationId: Int) {
    context.favouritesDataStore.updateData { currentFavourites ->
        val updatedFavourites = currentFavourites.favouritesList.filter { it.id != affirmationId }
        currentFavourites.toBuilder()
            .clearFavourites()
            .addAllFavourites(updatedFavourites)
            .build()
    }
}


// Extensions for stats conversion

private fun List<Pair<String, Int>>.toProto(): List<Stat> {
    return map { (name, value) ->
        Stat.newBuilder()
            .setName(name)
            .setValue(value)
            .build()
    }
}


private fun List<Stat>.toDomain(): List<Pair<String, Int>> {
    return map { stat -> stat.name to stat.value }
}

