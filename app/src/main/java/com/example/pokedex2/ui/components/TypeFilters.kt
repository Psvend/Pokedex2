package com.example.pokedex2.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex2.data.DataPokeTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeFilterUI(modifier: Modifier) {
    val pokeTypes = DataPokeTypes().loadTypes()
    val selectionMap = remember { mutableStateMapOf<Int, Boolean>() }
    var searchQuery by remember { mutableStateOf("Name, number or description") }
    var active by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var acceptSearchCriteria by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black),
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {},
                active = acceptSearchCriteria,
                onActiveChange = { active = it },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchQuery = ""
                            active = false
                            acceptSearchCriteria = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear, // Choose an icon, e.g., Clear or Close icon
                            contentDescription = "Clear Search",
                            tint = Color.Gray
                        )
                    }
                }
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    if (searchQuery.isNotBlank()) {
                        Text(
                            text = "Search Query: $searchQuery",
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
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                columns = StaggeredGridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(22.dp),
                verticalItemSpacing = 30.dp
            ) {
                items(pokeTypes) { localPokeType ->
                    val isSelected = selectionMap[localPokeType.id] ?: false

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.75f)
                            .background(
                                color = if (isSelected) Color(android.graphics.Color.parseColor(localPokeType.color)) else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            //.clip(RoundedCornerShape(100.dp)) No rounded corners?
                            .clickable {
                                selectionMap[localPokeType.id] = !isSelected
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = localPokeType.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            pokeTypes.forEach { localPokeType ->
                                selectionMap[localPokeType.id] = true
                            }
                        }
                    ) {
                        Text(text = "Show all types")
                    }
                    Button(
                        onClick = {
                            if (selectionMap.containsValue(true) || searchQuery.isNotBlank() && searchQuery != "Name, number or description") {
                                acceptSearchCriteria = true
                            } else
                                showDialog = true
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text(text = "No Search Criteria") },
            text = { Text(text = "Nothing has been chosen") }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun TypeFilterUIPreview() {
    TypeFilterUI(modifier = Modifier)
}