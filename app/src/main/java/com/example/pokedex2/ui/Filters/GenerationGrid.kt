package com.example.pokedex2.ui.Filters

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.model.LocalGenerations
import com.example.pokedex2.utils.RotatingLoader


@Composable
fun GenerationGrid(
    modifier: Modifier = Modifier,
    generations: List<LocalGenerations>,
    onToggleSelection: (Int) -> Unit,
    getColor: (Int) -> Color,
    selectedGeneration: MutableState<Int?>
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
        .padding(horizontal = 18.dp, vertical = 5.dp),
    columns = StaggeredGridCells.Fixed(5),
    horizontalArrangement = Arrangement.spacedBy(15.dp),
    verticalItemSpacing = 16.dp
) {
    if (generations.isEmpty()) {
        item {
            RotatingLoader()
        }
    }
    items(generations) { localGeneration ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .border(1.dp, Color.White, RoundedCornerShape(25.dp))
                    .background(
                        color = getColor(localGeneration.id),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .clickable {
                        onToggleSelection(localGeneration.id)
                        selectedGeneration.value = localGeneration.id
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = localGeneration.generation,
                    style = MaterialTheme.typography.bodyMedium,
                    color =  Color.White
                )
            }
        }
    }
}