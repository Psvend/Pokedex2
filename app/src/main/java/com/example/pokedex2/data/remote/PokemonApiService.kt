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
    suspend fun getPokemonEncounters(
        @Url encountersUrl: String
    ): List<EncounterResponse>


    @GET("ability/{idOrName}")
    suspend fun getAbility(
        @Path("idOrName") idOrName: String
    ): AbilityResponse

    @GET("pokemon-species/{idOrName}")
    suspend fun getPokemonSpecies(
        @Path("idOrName") idOrName: String
    ): PokemonSpecies

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutionChain(
        @Path("id") id: Int
    ): EvolutionChainResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonStats(
        @Path("name") name: String
    ): testPokemon




    companion object {
            const val BASE_URL= "https://pokeapi.co/api/v2/"
    }
}