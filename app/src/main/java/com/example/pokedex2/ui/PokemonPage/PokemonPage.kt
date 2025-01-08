package com.example.pokedex2.ui.PokePage


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex2.viewModel.PokePageViewModel


@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    viewModel: PokePageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }

    // UI
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            pokemonDetail != null -> {
                Text(
                    text = pokemonDetail?.name?: "Loading...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }
}









