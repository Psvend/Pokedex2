package com.example.pokedex2.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<PokemonDto>

    companion object {
            const val BASE_URL= "https://pokeapi.co/api/v2/"
    }
}