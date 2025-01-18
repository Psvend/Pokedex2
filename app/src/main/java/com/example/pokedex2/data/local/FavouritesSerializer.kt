package com.example.pokedex2.data.local

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import com.example.pokedex2.proto.FavouriteAffirmations


object FavouritesSerializer : Serializer<FavouriteAffirmations> {
    override val defaultValue: FavouriteAffirmations = FavouriteAffirmations.newBuilder().build()

    override suspend fun readFrom(input: InputStream): FavouriteAffirmations {
        // Use `GeneratedMessageLite.parseFrom` for reading
        return FavouriteAffirmations.parseFrom(input)
    }

    override suspend fun writeTo(t: FavouriteAffirmations, output: OutputStream) {
        // Call `writeTo` on the Protobuf object
        t.writeTo(output)
    }
}
