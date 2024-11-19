package com.example.pokedex2.data.remote

data class PokemonDto(
    val name: String,
    val number: Number,
    val type: List<String>,
    val image: String?
)
