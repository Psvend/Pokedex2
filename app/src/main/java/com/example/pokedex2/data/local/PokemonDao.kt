package com.example.pokedex2.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDao {
    @Upsert
    suspend fun upsert(pokemon:List<PokemonEntity>)

    @Query("SELECT * FROM PokemonEntity")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM PokemonEntity")
    suspend fun clearAll()
}