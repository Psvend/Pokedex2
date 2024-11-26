package com.example.pokedex2.ui.theme

/*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.viewModel.PokemonPageViewModel
import com.example.pokedex2.utils.RotatingLoader



@Composable
fun PokemonPage2(
    viewModel: PokemonPageViewModel = hiltViewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    // Fetch the Pokemon detail (you can call this based on some event or condition)
    viewModel.fetchPokemonDetail("bulbasaur") // Example name

    // Display the Pokemon detail
    pokemonDetail?.let { detail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Name: ${detail.name}", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "ID: ${detail.id}", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(model = detail.sprites.front_default),
                contentDescription = "Pokemon Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
            // Add more fields as needed
        }
    } ?: run {
        // Display a loading or error state
        RotatingLoader()
    }
}


 */