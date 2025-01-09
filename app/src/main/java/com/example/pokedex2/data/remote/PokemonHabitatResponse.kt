package com.example.pokedex2.data.remote

data class PokemonHabitatResponse(
    val id: Int,
    val name: String,
    val pokemon_species: List<PokemonSpecies>
)

data class PokemonSpecies (
    val name: String,
    val url: String
)
