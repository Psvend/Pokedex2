package com.example.pokedex2.ui.PokemonPage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun PokemonNr(id: Int){
    Column {
        Text(
            text = "#$id" ?: "Loading...",
            style = TextStyle(
                fontSize = 24.sp,
            ),
            color = Color.DarkGray
        )
    }
}