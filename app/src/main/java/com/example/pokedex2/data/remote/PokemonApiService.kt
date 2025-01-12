package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.testPokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

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


    @GET
    suspend fun getPokemonEncounters(@Url encountersUrl: String
    ): List<EncounterResponse>

    @GET
    suspend fun getPokemonForms(@Url formsUrl: String
    ): FormsResponse

    companion object {
            const val BASE_URL= "https://pokeapi.co/api/v2/"
    }
}