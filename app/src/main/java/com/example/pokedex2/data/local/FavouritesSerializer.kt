package com.example.pokedex2.data.local

import androidx.datastore.core.Serializer
import com.example.pokedex.Favourites
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/*
object FavouritesSerializer : Serializer<Favourites> {
    override val defaultValue: Favourites = Favourites.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Favourites {
        return try {
            Favourites.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Favourites, output: OutputStream) {
        t.writeTo(output)
    }
}


 */