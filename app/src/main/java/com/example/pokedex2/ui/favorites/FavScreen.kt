package com.example.pokedex2.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Holder for a fav-Pokemon from the local statastore
 */
@Composable
fun FavViewModel(modifier: Modifier = Modifier) {
    val placeholderList = listOf(1, 2, 3) //Using id from pokemons?
    val viewModel = com.example.pokedex2.viewModel.FavViewModel()


}