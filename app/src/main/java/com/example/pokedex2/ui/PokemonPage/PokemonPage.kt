package com.example.pokedex2.ui.PokePage


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import coil.compose.AsyncImage
import com.example.pokedex2.viewModel.PokePageViewModel
import androidx.compose.foundation.layout.Spacer



@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    viewModel: PokePageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    //Add new items here to show
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonImage by viewModel.pokemonImage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }


    //UI box to handle name  from api
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(120.dp),
        contentAlignment = Alignment.TopStart
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
                //Show the name of the pokemon from api
                Text(
                    text = pokemonDetail?.name ?: "Loading...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }

            else -> {
                CircularProgressIndicator()
            }

        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // UI box to handle image
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
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
                //Show image from api
                AsyncImage(
                    model = pokemonDetail?.sprites?.front_default,
                    contentDescription = "{pokemonDetail?.name} sprite",
                    modifier = Modifier.size(500.dp)
                )
            }

            else -> {
                CircularProgressIndicator()
            }
        }
    }


}














