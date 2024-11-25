package com.example.pokedex2.ui.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
//import com.example.pokedex2.viewModel.FavoritesViewModel

/**
 * Holder for a fav-Pokemon from the local statastore
 */
/*
@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val favoriteItems by favoritesViewModel.getFavorites().observeAsState(emptyList())

    LazyColumn {
        items(favoriteItems) { affirmation ->
            Text(text = affirmation.description)
        }
    }
}
*/