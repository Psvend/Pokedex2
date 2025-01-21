package com.example.pokedex2.ui.PokemonPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun PokemonStatsGraph(
    stats: List<Pair<String, Int>>,
    viewModel: PokemonPageViewModel
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stats",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            stats.forEach { (name, value) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Color.DarkGray
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                    ) {
                        val progress = value / 255f // Scale to [0, 1]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .fillMaxHeight()
                                .background(
                                    viewModel.getStatColor(name),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                    }

                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End),
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
