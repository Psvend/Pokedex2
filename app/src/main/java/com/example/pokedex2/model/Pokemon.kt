package com.example.pokedex2.model

import com.example.pokedex2.data.remote.json.Sprites


//Every affirmation consist of a pic and a string
data class Pokemon(
    val name: String,
    val number: Int,
    val type: List<String>,
    val image: String?,
    val sprites: Sprites
)





