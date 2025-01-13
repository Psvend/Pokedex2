package com.example.pokedex2.data.remote

data class PokemonFormResponse(
    val id: Int,
    val name: String,
    val order: Int,
    val form_order: Int,
    val is_default: Boolean,
    val is_battle_only: Boolean,
    val is_mega: Boolean,
    val form_name: String?,
    val pokemon: NamedAPIResource,
    val types: List<PokemonFormType>,
    val sprites: PokemonFormSprites,
    val version_group: NamedAPIResource,
    val names: List<Name>,
    val form_names: List<Name>
)

data class PokemonFormType(
    val slot: Int,
    val type: NamedAPIResource
)

data class PokemonFormSprites(
    val front_default: String?,
    val front_shiny: String?,
    val back_default: String?,
    val back_shiny: String?
)

data class NamedAPIResource(
    val name: String,
    val url: String
)

data class Name(
    val name: String,
    val language: NamedAPIResource
)
