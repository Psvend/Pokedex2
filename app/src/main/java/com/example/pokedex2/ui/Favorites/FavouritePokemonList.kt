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
import com.example.pokedex2.viewModel.PrimaryViewModel

@Composable
fun FavouritePokemonList(
    pokePageViewModel: PrimaryViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier,


    ) {
    val favouritePokemons by pokePageViewModel.pokemonLikedList.collectAsState()
    val pokemonDetail by pokePageViewModel.pokemonDetail.collectAsState()
    val pokemonDetailList by pokePageViewModel.pokemonDetailList.collectAsState()
    val pokemonLikedList by pokePageViewModel.pokemonLikedList.collectAsState()


    LaunchedEffect (pokemonLikedList) {
        pokePageViewModel.getAllLikedPokemons()
    }


    val affirmationList = pokePageViewModel.convertToAffirmation(pokemonLikedList)
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
                            pokePageViewModel.toggleLike(affirmation.name)
                        },
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        }
    }
}
