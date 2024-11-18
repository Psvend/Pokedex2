package com.example.pokedex2.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import com.example.pokedex2.viewModel.SearchViewModel

@Composable
fun TypeSelectionGrid(viewModel: SearchViewModel, modifier: Modifier = Modifier ) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp,

    ) {
        items(viewModel.pokeTypes) { localPokeType ->
            val isSelected = viewModel.selectionMap[localPokeType.id] ?: false
            val backgroundColor = viewModel.getTypeColor(localPokeType.id, localPokeType.color)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .aspectRatio(3.0f)
                    .background(
                        color = if (isSelected) backgroundColor else Color.Black,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clickable { viewModel.toggleSelection(localPokeType.id) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = localPokeType.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) Color.Black else Color.White
                )
            }
        }
    }
}
