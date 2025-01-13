package com.example.pokedex2.ui.PokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.R
import com.example.pokedex2.viewModel.FavouritesViewModel
import com.example.pokedex2.viewModel.SyncViewModel


@Composable
fun FavouritePokemonList(
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Collect the list of favourite Pokémon from FavouritesViewModel
    val favouritePokemons by favouritesViewModel.getFavouriteAffirmations().collectAsState(initial = emptyList())

    if (favouritePokemons.isEmpty()) {
        // Show empty state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.bug_image),
                contentDescription = "Empty List",
                modifier = Modifier.size(128.dp)
            )
            Text("No Pokémon added to favorites yet!")
        }
    } else {
        // Show list of favourite Pokémon
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(favouritePokemons) { affirmation ->
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

