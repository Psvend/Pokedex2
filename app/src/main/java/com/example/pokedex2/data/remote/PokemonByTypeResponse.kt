package com.example.pokedex2.data.remote

import com.example.pokedex2.model.Pokemon

data class PokemonByTypeResponse(
    val typeId: Int,
    val pokemonByType: List<Pokemon>,
)