package com.example.pokedex2.data.remote

data class PokemonDto (
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val base_experience: Int,
    val is_default: Boolean,
    val order: Int
    )
    data class Sprites(
    val front_default: String?
    )
    data class PokemonType(
        val slot: Int,
        val type: TypeDetail
    )

    data class TypeDetail(
        val name: String,
        val url: String
    )
