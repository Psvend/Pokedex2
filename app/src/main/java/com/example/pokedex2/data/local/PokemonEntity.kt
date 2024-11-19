package com.example.pokedex2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey
    val name: String,
    val number: Number,
    val type: List<String>,
    val image: String?
)
