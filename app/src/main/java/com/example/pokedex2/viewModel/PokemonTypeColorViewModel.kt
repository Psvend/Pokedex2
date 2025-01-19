package com.example.pokedex2.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class PokemonTypeColorViewModel: ViewModel() {
    fun getTypeColor(type: String): Color {
        return when (type.lowercase()) {
            "fire" -> Color(0xFFEE8130) // Red
            "grass" -> Color(0xFF7AC74C) // Green
            "water" -> Color(0xFF6390F0) // Blue
            "electric" -> Color(0xFFF7D02C) // Yellow
            "bug" -> Color(0xFFA6B91A) // Light Green
            "poison" -> Color(0xFFA33EA1) // Purple
            "ice" -> Color(0xFF96D9D6) // Cyan
            "normal" -> Color(0xFFA8A77A) // White
            "ground" -> Color(0xFFE2BF65) // Brown
            "flying" -> Color(0xFFA98FF3) // Light Blue
            "fairy" -> Color(0xFFD685AD) // Pink
            "fighting" -> Color(0xFFC22E28) // Reddish Brown
            "psychic" -> Color(0xFFF95587) // Hot Pink
            "dragon" -> Color(0xFF6F35FC) //Light Blue
            "dark" -> Color(0xFF705746)
            "ghost" -> Color(0xFF735797)
            "rock" -> Color(0xFFB6A136)
            "steel" -> Color(0xFFB7B7CE)
            else -> Color.Gray // Default Gray
        }
    }
}