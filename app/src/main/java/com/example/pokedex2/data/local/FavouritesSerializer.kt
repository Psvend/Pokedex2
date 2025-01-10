package com.example.pokedex2.data.local

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import com.example.pokedex2.proto.FavouriteAffirmations
import com.example.pokedex2.proto.FavouriteAffirmation



object FavouritesSerializer : Serializer<FavouriteAffirmations> {
    override val defaultValue: FavouriteAffirmations = FavouriteAffirmations.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): FavouriteAffirmations {
        return FavouriteAffirmations.parseFrom(input)
    }

    override suspend fun writeTo(t: FavouriteAffirmations, output: OutputStream) {
        t.writeTo(output)
    }
}