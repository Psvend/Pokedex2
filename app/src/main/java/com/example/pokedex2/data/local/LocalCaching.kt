package com.example.pokedex2.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class LocalCaching(
    @PrimaryKey val id: Int,
    val name: String,
    val imageResourceId: String,
    val typeIcon: String,
    val isLiked: Boolean,
    val number: Int,
    val ability: String,
    val heldItem: String,
    val stats: String
)