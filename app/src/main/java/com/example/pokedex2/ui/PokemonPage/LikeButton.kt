package com.example.pokedex2.ui.PokemonPage

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.MainPageViewModel

@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
    mainPageViewModel: MainPageViewModel
) {


    val isTrue = mainPageViewModel.getAllLikedPokemons().find { it?.name  == affirmation.name }
        ?.equals(affirmation)


    IconButton(onClick = {onLikeClicked() },
        modifier = modifier) {
        Icon(
            imageVector = if (isTrue == true) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = if (isTrue == true) "Toggled Icon" else "Default Icon",
            tint = Color(0xFFB11014),
            modifier = Modifier
                .size(40.dp)

        )
    }
}