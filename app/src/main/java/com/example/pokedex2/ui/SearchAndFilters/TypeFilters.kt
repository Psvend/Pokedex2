package com.example.pokedex2.ui.SearchAndFilters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex2.model.LocalPokeTypes
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeFilterUI(
    showOverlay: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isLoading = searchViewModel.isLoading.value
    val pokeTypes = searchViewModel.pokeTypes.value
    val selectionMap = searchViewModel.selectionMap
    val searchQuery = searchViewModel.searchQuery
    val allTypesSelected = selectionMap.values.all { it } // Check if all are selected
    val showDialog = searchViewModel.showDialog
    val acceptSearchCriteria = searchViewModel.acceptSearchCriteria
    if (showOverlay) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black)
            ) {
                // Search Bar
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused && searchQuery.value == "Name, number or description") {
                                searchViewModel.searchQuery.value = ""
                            } else {
                                searchViewModel.searchQuery.value = "Name, number or description"
                            }
                        },
                    query = searchQuery.value,
                    onQueryChange = { searchViewModel.searchQuery.value = it },
                    onSearch = {},
                    active = acceptSearchCriteria.value,
                    onActiveChange = { searchViewModel.active.value = it },
                    trailingIcon = {
                        IconButton(
                            onClick = { searchViewModel.clearSearch() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search",
                                tint = Color.Gray
                            )
                        }
                    }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        if (searchQuery.value.isNotBlank()) {
                            Text(
                                text = "Search Query: ${searchQuery.value}",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        val selectedTypes = pokeTypes.filter { selectionMap[it.id] == true }
                        if (selectedTypes.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Selected Types:",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            selectedTypes.forEach { type ->
                                Text(
                                    text = type.name,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Choose type filter",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )

                // Conditional TypeGrid
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            RotatingLoader()
                        }
                    }

                    pokeTypes.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No types available", color = Color.Red)
                        }
                    }

                    else -> {
                        TypeGrid(
                            modifier = Modifier,
                            pokeTypes = pokeTypes,
                            selectionMap = selectionMap,
                            onToggleSelection = { id -> searchViewModel.toggleSelection(id) },
                            getTypeColor = { id, color -> searchViewModel.getTypeColor(id, color) }
                        )
                    }
                }

                // Buttons
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 22.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                if (allTypesSelected) {
                                    // Deselect all types
                                    pokeTypes.forEach { selectionMap[it.id] = false }
                                } else {
                                    // Select all types
                                    pokeTypes.forEach { selectionMap[it.id] = true }
                                }
                            }
                        ) {
                            Text(text = if (allTypesSelected) "Deselect all" else "Show all types")
                        }
                        Button(
                            onClick = { searchViewModel.validateCriteria() }
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }

        // Dialog
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { searchViewModel.showDialog.value = false },
                confirmButton = {
                    Button(onClick = { searchViewModel.showDialog.value = false }) {
                        Text("OK")
                    }
                },
                title = { Text(text = "No Search Criteria") },
                text = { Text(text = "Nothing has been chosen") }
            )
        }
    }
}

/*
@Composable
fun TypeGrid(
    modifier: Modifier = Modifier,
    pokeTypes: List<LocalPokeTypes>,
    selectionMap: Map<Int, Boolean>,
    onToggleSelection: (Int) -> Unit,
    getTypeColor: (Int, String) -> Color
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            //.weight(1f)
            .padding(horizontal = 18.dp, vertical = 12.dp),
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalItemSpacing = 32.dp
    ) {
        if (pokeTypes.isEmpty()) {
            item {
                RotatingLoader() // Show a loader while the data is empty
            }
        }
        items(pokeTypes) { localPokeType ->
            val isSelected = selectionMap[localPokeType.id] ?: false

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
                        onToggleSelection(localPokeType.id)
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
*/
