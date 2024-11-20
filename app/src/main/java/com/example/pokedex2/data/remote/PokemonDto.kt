package com.example.pokedex2.data.remote

class PokemonDto (
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>
    )

    data class PokemonType(
        val slot: Int,
        val type: TypeDetail
    )

    data class TypeDetail(
        val name: String,
        val url: String
    )
