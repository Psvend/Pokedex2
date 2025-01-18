package com.example.pokedex2.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Pokemon
import com.example.pokedex2.viewModel.CatchPokemonViewModel


@Composable
fun CatchPokemonScreen(
    viewModel: CatchPokemonViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentPokemon by viewModel.currentPokemon.collectAsState()
    val isAnimationActive by viewModel.isAnimationActive.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9D9D9)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentPokemon != null) {
            PokemonDetailsDialog(
                pokemon = currentPokemon!!,
                onDismiss = { viewModel.stopAnimation() }
            )
        } else {
            Text(
                text = "No Pokémon added to favorites yet!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.fetchRandomPokemon()
                viewModel.playSound(context)
                viewModel.startAnimation()
            }) {
                Text("Catch 'em all!")
            }
        }
        if (isAnimationActive) {
            CatchAnimation(isActive = true)
        }
    }
}

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
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    //modifier = Modifier.align(Alignment.CenterHorizontally) // Center title horizontally
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
                Text(
                    text = "Type: ${pokemon.type.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center text explicitly
                )
            }
        },
        confirmButton = {}, // Empty confirm button since the Pokémon is saved directly
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
