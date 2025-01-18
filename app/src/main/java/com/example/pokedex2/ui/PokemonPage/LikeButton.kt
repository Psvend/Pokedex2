package com.example.pokedex2.ui.PokemonPage

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation

@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
) {
    IconButton(onClick = onLikeClicked,
        modifier = modifier) {
        Icon(
            painter = painterResource(
                if (affirmation.isLiked) R.drawable.heart_filled else R.drawable.heart_empty
            ),
            contentDescription = if (affirmation.isLiked) "Unlike" else "Like",
            tint = if (affirmation.isLiked) Color(0xFFB11014) else Color(0xFFB11014)
        )
    }
}