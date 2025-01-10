package com.example.pokedex2.ui.PokemonList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

/*
@Composable
fun FavouritePokemonList(
    viewModel: FavouritesViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val favouriteAffirmations by viewModel.getFavouriteAffirmations().collectAsState(initial = emptyList())

    if (favouriteAffirmations.isEmpty()) {
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
            items(favouriteAffirmations) { affirmation ->
                AffirmationCard(
                    affirmation = affirmation,
                    navController = navController,
                    onLikeClicked = {
                        viewModel.removeFavourite(affirmation.id)
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

 */
