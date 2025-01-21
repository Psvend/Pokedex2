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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.FilterViewModel

@Composable
fun FilterOverlay(
    showOverlay: Boolean,
    onClose: () -> Unit,
    filterViewModel: FilterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onFilterApply: (String, ClosedRange<Int>?)-> Unit
) {
    val isLoading = filterViewModel.isLoading.value
    val selectionMap = filterViewModel.selectionMap
    val selectionGenMap = filterViewModel.selectionGenMap
    val allTypesSelected = selectionMap.values.all { it } // Check if all are selected
    val allGensSelected = selectionGenMap.values.all {it}
    val pokeTypes = filterViewModel.pokeTypes.value
    val pokeGens = filterViewModel.pokeGenerations.value

    val typesFilter = remember { mutableStateOf("") }
    val generationsFilter = remember { mutableStateOf<ClosedRange<Int>?> (null)}


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
                            onToggleSelection = { id -> filterViewModel.toggleSelection(id) },
                            getTypeColor = { id, color -> filterViewModel.getTypeColor(id, color)},
                            selectedType = typesFilter.value,
                            onTypeSelected = { type -> typesFilter.value = type }
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
                            onToggleSelection = {id -> filterViewModel.toggleSelection(id)},
                            getColor = {id -> filterViewModel.getButtonColor(id)},
                            generationsFilter = generationsFilter
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
                            onClose()
                            onFilterApply(typesFilter.value, generationsFilter.value)
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