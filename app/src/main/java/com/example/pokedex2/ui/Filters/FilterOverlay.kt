package com.example.pokedex2.ui.Filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.SearchViewModel

@Composable
fun FilterOverlay(
    showOverlay: Boolean,
    onClose: () -> Unit,
    searchViewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isLoading = searchViewModel.isLoading.value
    val selectionMap = searchViewModel.selectionMap
    val selectionGenMap = searchViewModel.selectionGenMap
    val allTypesSelected = selectionMap.values.all { it } // Check if all are selected
    val allGensSelected = selectionGenMap.values.all {it}
    val pokeTypes = searchViewModel.pokeTypes.value
    val pokeGens = searchViewModel.pokeGenerations.value


    if(showOverlay){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f)),
        ) {

            Column (
                modifier = Modifier
                    .background(Color(0xFFFFF9E6))
                    .padding(16.dp)
                    .padding(bottom = 30.dp, top = 0.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Filters",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { onClose() },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = Color.DarkGray,
                            contentDescription = "Close FilterOverlay",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
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
                        Box(
                            modifier = Modifier
                                .align(Alignment.Start)
                        ){
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = "Types:",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        TypeGrid(
                            modifier = Modifier,
                            pokeTypes = pokeTypes,
                            selectionMap = selectionMap,
                            onToggleSelection = { id -> searchViewModel.toggleSelection(id) },
                            getTypeColor = { id, color -> searchViewModel.getTypeColor(id, color) }
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Box(
                            modifier = Modifier
                                .align(Alignment.Start)
                        ){
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = "Generations:",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        GenerationGrid(
                            modifier = Modifier,
                            generations = pokeGens,
                            onToggleSelection = {id -> searchViewModel.toggleSelection(id)},
                            getColor = {id -> searchViewModel.getButtonColor(id)}
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {onClose()},
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4))
                    ) {
                        Text(
                            text = "Cancel"
                        )
                    }
                    Button(
                        onClick = {
                            if (allTypesSelected) {
                                pokeTypes.forEach { selectionMap[it.id] = false }
                                pokeGens.forEach{selectionGenMap[it.id] = false}
                            } else {
                                pokeTypes.forEach { selectionMap[it.id] = true }
                                pokeGens.forEach{selectionGenMap[it.id] = true}
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4))
                    ) {
                        Text(
                            text = if (allTypesSelected && allGensSelected) "Deselect all" else "Select all",
                        )
                    }
                    Button(
                        onClick = {
                            onClose()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4))
                    ) {
                        Text(
                            text = "Confirm"
                        )
                    }
                }
            }
        }
    }
}