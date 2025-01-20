package com.example.pokedex2.ui.PokemonPage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokedex2.ui.Filters.AddSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun PokemonGrowthRate(
    growthRate: String,
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
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Growth Rate",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Progress Bar
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    val progress = viewModel.getGrowthRateProgress(growthRate)
                    val color = viewModel.getGrowthRateColor(growthRate)

                    Log.d("PokemonGrowthRate", "Progress: $progress, Color: $color")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .background(color, shape = RoundedCornerShape(10.dp))
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = growthRate.capitalizeFirstLetter().AddSpaceAndCapitalize(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                    )
                )
            }
        }
    }
}