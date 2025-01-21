package com.example.pokedex2.ui.PokemonPage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter

@Composable
fun PokemonName(name: String) {
    Column {
        Text(
            text = name.capitalizeFirstLetter().addSpaceAndCapitalize() ?: "Loading...",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                color = Color.DarkGray
            ),
            color = Color.DarkGray
        )
    }
}