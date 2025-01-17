package com.example.pokedex2.ui.SearchAndFilters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.model.LocalEvolution
import com.example.pokedex2.utils.RotatingLoader

@Composable
fun EvolutionGrid(
    modifier: Modifier = Modifier,
    evolutions: List<LocalEvolution>,
    selectionEvoMap: Map<Int, Boolean>,
    onToggleSelection: (Int) -> Unit,
    getColor: (Int) -> Color
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            //.weight(1f)
            .padding(horizontal = 18.dp, vertical = 5.dp),
        columns = StaggeredGridCells.Fixed(5),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalItemSpacing = 16.dp
    ) {
        if (evolutions.isEmpty()) {
            item {
                RotatingLoader() // Show a loader while the data is empty
            }
        }
        items(evolutions) { localEvolution ->
            val isSelected = selectionEvoMap[localEvolution.id] ?: false

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.0f)
                    .border(1.dp, Color.White, RoundedCornerShape(25.dp))
                    .background(
                        color = getColor(localEvolution.id),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .clickable {
                        onToggleSelection(localEvolution.id)
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = localEvolution.stage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) Color(0xFFFFD88E) else Color.White
                )
            }
        }
    }

}

