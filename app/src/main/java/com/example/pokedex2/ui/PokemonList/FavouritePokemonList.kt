package com.example.pokedex2.ui.PokemonList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.viewModel.FavouritesViewModel
import com.example.pokedex2.viewModel.SyncViewModel


@Composable
fun FavouritePokemonList(
    syncViewModel: SyncViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Collect the synced list of liked Pokémon
    val favouriteAffirmations by syncViewModel.pokemonList.collectAsState(initial = emptyList())

    // Filter the list to show only liked Pokémon
    val likedPokemons = favouriteAffirmations.filter { it.isLiked }

    if (likedPokemons.isEmpty()) {
        // Show empty state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorite Pokémon added yet!")
        }
    } else {
        // Show list of favourite Pokémon
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(likedPokemons) { affirmation ->
                AffirmationCard(
                    affirmation = affirmation,
                    navController = navController,
                    onLikeClicked = {
                        // Call toggleLike from SyncViewModel
                        syncViewModel.toggleLike(affirmation)
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
