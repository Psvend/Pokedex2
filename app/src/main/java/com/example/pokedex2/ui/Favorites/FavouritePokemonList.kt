package com.example.pokedex2.ui.Favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.ui.HomePage.AffirmationCard
import com.example.pokedex2.ui.components.EmptyStateScreen
import com.example.pokedex2.viewModel.FavouritesViewModel
import com.example.pokedex2.viewModel.SyncViewModel


@Composable
fun FavouritePokemonList(
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    // Collect the list of favourite Pokémon from FavouritesViewModel
    val favouritePokemons by favouritesViewModel.getFavouriteAffirmations().collectAsState(initial = emptyList())

    val sortedFavouritePokemons = favouritePokemons.sortedBy { it.number }

    if (sortedFavouritePokemons.isEmpty()) {
        // Show empty state. Point to catch pokemon with nav
        EmptyStateScreen(modifier = modifier)

    } else {
        // Show list of favourite Pokémon
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(sortedFavouritePokemons) { affirmation ->
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
