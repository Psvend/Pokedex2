package com.example.pokedex2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val types: List<String>,
    val baseExperience: Int,
    val isDefault: Boolean,
    val order: Int,
    val isLiked: Boolean
) {
}
