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
}
