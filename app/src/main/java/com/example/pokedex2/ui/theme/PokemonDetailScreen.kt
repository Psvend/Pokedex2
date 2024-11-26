package com.example.pokedex2.ui.theme

/*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.PokeViewModel
import com.example.pokedex2.viewModel.TestViewModel


@Composable
    fun PokemonDetailScreen(
        pokeViewModel: PokeViewModel,
        modifier: Modifier = Modifier,
        pokemonDetailViewModel: TestViewModel = viewModel() // Use the ViewModel
    ) {
        // Observe the state
        val pokemonState by pokemonDetailViewModel.pokemonTest.collectAsState()

        // Launch the observer to start listening to pokemon details
        LaunchedEffect(Unit) {
            pokemonDetailViewModel.fetchPokemonDetail(pokeViewModel)
        }

        // Main content
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { pokemonState?.let { pokemon ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pokemon Image
                pokemon.imageUrl.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(8.dp)
                            .clip(CircleShape)
                    )
                }

                // Pokemon Name
                Text(
                    text = pokemon.name,
                    style =
                    MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )

                // Pokemon Types
                Text(
                    text = "Types: ${pokemon.types.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )

                // Pokemon Description
                Text(
                    text = pokemon.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        } ?: run {
                // Loading State
            RotatingLoader()
        }
    }
}
*/


