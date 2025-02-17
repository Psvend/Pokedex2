package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.PokemonResult

data class PokemonListDtoDto(
    val count : Int,
    val next : String?,
    val previous : String?,
    val results : List<PokemonResult>
)
