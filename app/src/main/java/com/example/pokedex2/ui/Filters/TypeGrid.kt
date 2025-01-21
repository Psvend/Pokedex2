package com.example.pokedex2.ui.Filters

import android.util.Log
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.model.LocalPokeTypes
import com.example.pokedex2.utils.RotatingLoader

@Composable
fun TypeGrid(
    modifier: Modifier = Modifier,
    pokeTypes: List<LocalPokeTypes>,
    selectionMap: Map<Int, Boolean>,
    onToggleSelection: (Int) -> Unit,
    getTypeColor: (Int, String) -> Color,
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            //.weight(1f)
            .padding(horizontal = 18.dp, vertical = 5.dp),
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalItemSpacing = 12.dp
    ) {
        if (pokeTypes.isEmpty()) {
            item {
                RotatingLoader()
            }
        }
        items(pokeTypes) { localPokeType ->
            val isSelected = localPokeType.name == selectedType

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3.0f)
                    .border(1.dp, Color.White, RoundedCornerShape(25.dp))
                    .background(
                        color = getTypeColor(localPokeType.id, localPokeType.color),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .clickable {
                        onTypeSelected(localPokeType.name)
                    },
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

fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar { it.uppercase() }
}

fun String.addSpaceAndCapitalize(): String {
    val result = StringBuilder()
    var capitalizeNext = false

    for (char in this) {
        if (char == '-') {
            result.append(' ')
            capitalizeNext = true
        } else {
            if (capitalizeNext) {
                result.append(char.uppercaseChar())
                capitalizeNext = false
            } else {
                result.append(char)
            }
        }
    }

    return result.toString()
}

fun List<String>.capitalizeFirstLetter(): List<String> {
    return this.map { it.capitalizeFirstLetter() }
}

fun List<String>.addSpaceAndCapitalize(): List<String> {
    return this.map { it.addSpaceAndCapitalize() }
}

