package com.example.pokedex2.ui.theme
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.pokedex2.data.remote.PokemonResult
import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun PokemonListScreen(
    pokemon: LazyPagingItems<PokemonResult>,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = pokemon.loadState) {
        if (pokemon.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (pokemon.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (pokemon.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(pokemon.itemSnapshotList.items) { pokemonResult ->
                    PokemonCard(pokemon = pokemonResult)
                }
                item {
                    if (pokemon.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: PokemonResult) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = pokemon.name, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun PokemonPage2(
    viewModel: PokemonPageViewModel = hiltViewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    // Fetch the Pokemon detail (you can call this based on some event or condition)
    viewModel.fetchPokemonDetail("pikachu") // Example name

    // Display the Pokemon detail
    pokemonDetail?.let { detail ->
        // Display the details of the Pokemon
        Text(text = "Name: ${detail.name}")
        Text(text = "ID: ${detail.id}")
        Text(text = "Image URL: ${detail.sprites.front_default}")
        // Add more fields as needed
    } ?: run {
        // Display a loading or error state
        Text(text = "Loading...")
    }
}
