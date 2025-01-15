package com.example.pokedex2.model


data class Affirmation (
    val id: Int,
    val name: String,
    val imageResourceId: String,
    val typeIcon: List<String>,
    val isLiked: Boolean,
    val number: Int,
    val encounterLocations: List<String> = emptyList(),
    //val forms: List<String>
)
