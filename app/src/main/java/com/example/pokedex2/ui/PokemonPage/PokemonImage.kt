package com.example.pokedex2.ui.PokemonPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.SyncViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.painter.Painter


@Composable
fun PokemonImage(painter: Painter, syncViewModel: SyncViewModel, affirmation: Affirmation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .shadow(
                    16.dp,
                    shape = RoundedCornerShape(24.dp)
                )
                .border(16.dp, Color.Gray, shape = RoundedCornerShape(24.dp))
                .background(Color.White, shape = RoundedCornerShape(24.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = "Pokemon sprite",
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }

        LikeButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-50).dp, y = 25.dp)
                .clickable (
                    indication = null,
                    interactionSource = remember{ MutableInteractionSource() }
                ) {
                    syncViewModel.toggleLike(affirmation)
                },
            affirmation = affirmation,
        )

    }
}