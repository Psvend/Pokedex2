package com.example.pokedex2.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.pokedex2.proto.FavouriteAffirmations
import com.example.pokedex2.proto.FavouriteAffirmation


val Context.favouritesDataStore: DataStore<FavouriteAffirmations> by dataStore(
    fileName = "favourites.pb",
    serializer = FavouritesSerializer
)

