package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.NamedAPIResource
import com.google.gson.annotations.SerializedName


data class EvolutionChainResponse(
    val id: Int,
    val chain: ChainLink
)

data class ChainLink(
    @SerializedName("species") val species: NamedAPIResource,
    @SerializedName("evolves_to") val evolves_to: List<ChainLink> = emptyList(),
    @SerializedName("evolution_details") val evolution_details: List<EvolutionDetail> = emptyList(),
    @SerializedName("is_baby") val isBaby: Boolean = false
)