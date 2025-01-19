package com.example.pokedex2.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Pokemon
import com.example.pokedex2.ui.HomePage.PokemonTypeIcons
import com.example.pokedex2.viewModel.PokemonTypeColorViewModel

@Composable
fun CatchAnimation(isActive: Boolean) {
    {TODO("Make whole current view schrink into nothing when animation before animation. For a clear canvas/background")}
    val ballOffsetY = remember { androidx.compose.animation.core.Animatable(0f) } // Start ball top of screen

    //Animation
    if (isActive) {
        LaunchedEffect(Unit) {
            ballOffsetY.animateTo(
                targetValue = 1f, // Drop the ball 300 pixels down
                animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing)
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.closed_pokeball),
            contentDescription = "Pokeball",
            modifier = Modifier.offset(10.dp)
        )
    }
}


@Composable
fun PokemonDetailsDialog(
    pokemon: Pokemon,
    onDismiss: () -> Unit,
    typingColorViewModel: PokemonTypeColorViewModel = viewModel()
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth() // Make sure the column takes full width

            ) {
                Image(
                    painter = rememberAsyncImagePainter(pokemon.image),
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(){
                    PokemonTypeIcons(
                        types = pokemon.type,
                        modifier = Modifier,
                        fontSize = 10,
                        {type -> typingColorViewModel.getTypeColor(type)})
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4))
                ) {
                Text(
                    "Close"
                )
            }
        }
    )
}
