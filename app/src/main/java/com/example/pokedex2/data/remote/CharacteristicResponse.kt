package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.NamedAPIResource


data class CharacteristicResponse(
    val id: Int,
    val gene_modulo: Int,
    val descriptions: List<Description>
)

data class Description(
    val description: String,
    val language: NamedAPIResource
)