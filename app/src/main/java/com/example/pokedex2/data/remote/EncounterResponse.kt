package com.example.pokedex2.data.remote


data class EncounterResponse(
    val location_area: LocationArea
)

data class LocationArea(
    val name: String
)
