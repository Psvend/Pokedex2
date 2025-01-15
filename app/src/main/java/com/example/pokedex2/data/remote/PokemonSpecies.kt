package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.NamedAPIResource

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val growth_rate: NamedAPIResource, // Reference to the growth_rate object
    val evolution_chain: EvolutionChainUrl
)

data class EvolutionChainUrl(
    val url: String
)