package com.example.pokedex2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import javax.inject.Inject

@Dao
interface LocalCachingDao {
    @Query("SELECT * FROM pokemons")
    suspend fun getAllPokemons(): List<LocalCaching>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<LocalCaching>)

    @Query("SELECT * FROM pokemons WHERE name =:pokemonName")
    suspend fun getPokemonByname(pokemonName: String): LocalCaching

    @Query("SELECT * FROM pokemons WHERE isLiked =:isLiked")
    suspend fun getAllFavourites(isLiked: Boolean = true): List<LocalCaching>

    @Query("UPDATE pokemons SET isLiked = 1 WHERE name =:pokemonName ")
    suspend fun addLike(pokemonName : String)

    @Query("UPDATE pokemons SET isLiked = 0 WHERE name =:pokemonName ")
    suspend fun removeLike(pokemonName : String)


}
