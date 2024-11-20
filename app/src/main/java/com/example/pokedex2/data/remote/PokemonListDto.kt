package com.example.pokedex2.data.remote

data class PokemonListDtoDto(
    val count : Int,
    val next : String?,
    val previous : String?,
    val results : List<PokemonResult>
)

data class PokemonResult(
    val name : String,
    val url : String
)
