package com.example.pokedex2.data.remote
import com.example.pokedex2.data.remote.json.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class EvolutionDetailOlg(
    @SerializedName("item") val item: NamedAPIResource? = null,
    @SerializedName("trigger") val trigger: NamedAPIResource? = null,
    @SerializedName("gender") val gender: Int? = null,
    @SerializedName("held_item") val heldItem: NamedAPIResource? = null,
    @SerializedName("known_move") val knownMove: NamedAPIResource? = null,
    @SerializedName("known_move_type") val knownMoveType: NamedAPIResource? = null,
    @SerializedName("location") val location: NamedAPIResource? = null,
    @SerializedName("min_level") val min_level: Int? = null
)