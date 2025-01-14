package com.example.pokedex2.data.remote

import com.example.pokedex2.data.remote.json.NamedAPIResource


data class AbilityResponse(
    val id: Int,
    val name: String,
    val is_main_series: Boolean,
    val generation: NamedAPIResource,
    val effect_entries: List<VerboseEffect>,
    val flavor_text_entries: List<AbilityFlavorText>
)

data class VerboseEffect(
    val effect: String,
    val short_effect: String,
    val language: NamedAPIResource
)

data class AbilityFlavorText(
    val flavor_text: String,
    val language: NamedAPIResource,
    val version_group: NamedAPIResource
)


