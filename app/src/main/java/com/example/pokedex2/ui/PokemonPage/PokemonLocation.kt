package com.example.pokedex2.ui.PokemonPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PokemonLocation(locations: List<String>) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Encounters",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (locations.isEmpty()) {
                Text(
                    text = "This pokemon don't have specific encounter locations",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                    ),
                    textAlign = TextAlign.Start
                )
            } else {
                locations.forEach { location ->
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}