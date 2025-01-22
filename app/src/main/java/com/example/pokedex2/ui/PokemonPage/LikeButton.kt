package com.example.pokedex2.ui.PokemonPage

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.PokePageViewModel
import com.example.pokedex2.viewModel.SyncViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull

@SuppressLint("RememberReturnType")
@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    affirmation: Affirmation,
    syncViewModel: SyncViewModel = hiltViewModel(),
    pokePageViewModel: PokePageViewModel = hiltViewModel(),
    mainPageViewModel: MainPageViewModel = hiltViewModel(),

    ) {

    var checked by remember { mutableStateOf(affirmation.isLiked) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    IconToggleButton(
            checked = checked,
            onCheckedChange = { checked = it
                syncViewModel.toggleLike(affirmation)
            },
            interactionSource = interactionSource
        ) { Icon(
            imageVector = if (checked as Boolean) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = if (checked as Boolean) "Toggled Icon" else "Default Icon",
            tint = Color(0xFFB11014),
            modifier = Modifier
                .size(40.dp)
        )
        }
}