package com.example.pokedex2.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.data.remote.PokemonDto
import com.example.pokedex2.viewModel.PokeViewModel
@Composable
fun PokemonListScreen(
    viewModel: PokeViewModel = viewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonItem(pokemon)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = pokemon.name, style = MaterialTheme.typography.titleLarge)
        // Add more details about the Pokémon as needed
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPokemonListScreen() {
    PokemonListScreen(viewModel = PokeViewModel())
}