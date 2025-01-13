package com.example.pokedex2.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore


val Context.favouritesDataStore by dataStore(
    fileName = "favourites.pb",
    serializer = FavouritesSerializer
)

