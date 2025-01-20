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
import coil.compose.AsyncImage
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.SyncViewModel

@Composable
fun PokemonImage(model: String?, syncViewModel: SyncViewModel, affirmation: Affirmation) {
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
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = "Pokemon sprite",
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(24.dp))
                )
            } else {
                Text(
                    text = "Image not available",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        LikeButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(10.dp)
                .offset(x = (-25).dp, y = 25.dp), // Position the button
            affirmation = affirmation, // Pass the affirmation
            onLikeClicked = { syncViewModel.toggleLike(affirmation) } // Trigger the logic
        )

    }
}