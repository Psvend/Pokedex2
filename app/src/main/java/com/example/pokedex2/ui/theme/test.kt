package com.example.pokedex2.ui.theme
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.data.remote.PokemonResult
import com.example.pokedex2.viewModel.PokemonPageViewModel
import coil.compose.rememberImagePainter

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
        Text(text = "Loading...", textAlign = TextAlign.Center)
    }
}
