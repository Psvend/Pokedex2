package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.NamedAPIResource
import com.google.gson.annotations.SerializedName


data class EvolutionChainResponseOld(
    val id: Int,
    val chain: ChainLink
)

data class ChainLink(
    @SerializedName("species") val species: NamedAPIResource,
    @SerializedName("evolves_to") val evolves_to: List<ChainLink> = emptyList(),
    @SerializedName("evolution_details") val evolution_details: List<EvolutionDetail> = emptyList(),
    @SerializedName("is_baby") val isBaby: Boolean = false
)

data class EvolutionChainResponse(
    val baby_trigger_item: Any?,
    val chain: EvolutionChain,
    val id: Int
)

data class EvolutionChain(
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<Evolution>,
    val is_baby: Boolean,
    val species: Species
)

data class Evolution(
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<Evolution>,
    val is_baby: Boolean,
    val species: Species
)

data class EvolutionDetail(
    val min_level: Int?,
    val trigger: Trigger
)

data class Trigger(
    val name: String,
    val url: String
)

data class Species(
    val name: String,
    val url: String
)
