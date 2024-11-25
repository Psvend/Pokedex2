package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.testPokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListDtoDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): testPokemon

    companion object {
            const val BASE_URL= "https://pokeapi.co/api/v2/"
    }
}