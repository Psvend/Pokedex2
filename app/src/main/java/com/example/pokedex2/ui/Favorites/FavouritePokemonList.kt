package com.example.pokedex2.ui.Favorites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.ui.HomePage.AffirmationCard
import com.example.pokedex2.ui.components.EmptyStateScreen
import com.example.pokedex2.viewModel.MainPageViewModel

@Composable
fun FavouritePokemonList(
    mainPageViewModel: MainPageViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier,


    ) {
    val pokemonLikedList by mainPageViewModel.pokemonLikedList.collectAsState()


    LaunchedEffect (pokemonLikedList) {
        mainPageViewModel.getAllLikedPokemons()
    }


    val affirmationList = mainPageViewModel.convertToAffirmation(pokemonLikedList)
    val sortedFavouritePokemons = affirmationList.sortedBy { it?.number }

    Log.d("tag", "$affirmationList $sortedFavouritePokemons")


    if (sortedFavouritePokemons.isEmpty()) {
        EmptyStateScreen(modifier = modifier)

    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(sortedFavouritePokemons) { affirmation ->
                if (affirmation != null) {
                    AffirmationCard(
                        affirmation = affirmation,
                        navController = navController,
                        onLikeClicked = {
                            mainPageViewModel.toggleLike(affirmation)
                        },
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        }
    }
}
