package com.example.pokedex2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedex2.R
import com.example.pokedex2.viewModel.CatchPokemonViewModel
import kotlinx.coroutines.delay

@Composable
fun EmptyStateScreen(
    viewModel: CatchPokemonViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentPokemon by viewModel.currentPokemon.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (currentStep != CatchPokemonViewModel.AnimationStep.Idle) Color(0xffeae6d5) else Color(0xffeae6d5)),


    contentAlignment = Alignment.Center
    ) {
        when (currentStep) {
            CatchPokemonViewModel.AnimationStep.Idle -> {
                // Initial screen with button to start catching
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier.size(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bug_image),
                            contentDescription = "Empty List",
                            modifier.size(128.dp)
                        )
                    }
                    Spacer(modifier.height(16.dp))
                    Text(
                        text = "No Pokémon added to favorites yet!",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                    Spacer(modifier.height(16.dp))
                    Text(
                        text = "Catch your first?",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                    Spacer(modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.fetchRandomPokemon() },
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4)),
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Catch 'em all!",
                            color = Color(0xFFFDFDFD)
                        )
                    }
                }
            }
            CatchPokemonViewModel.AnimationStep.CatchAnimation -> {
                // Pokeball animation
                CatchAnimation(isActive = true)
                LaunchedEffect(Unit) {
                    delay(2000L) // Wait for the animation duration
                    viewModel.proceedToNextStep()
                }
            }
            CatchPokemonViewModel.AnimationStep.SparkleAnimation -> {
                // Sparkle animation and sound
                SparkleAnimation(isActive = true)
                LaunchedEffect(Unit) {
                    viewModel.playSound(context)
                    delay(2000L) // Wait for the sparkle duration
                    viewModel.proceedToNextStep()
                }
            }
            CatchPokemonViewModel.AnimationStep.ShowDialog -> {
                // Show Pokémon details dialog
                currentPokemon?.let { pokemon ->
                    PokemonDetailsDialog(
                        pokemon = pokemon,
                        onDismiss = { viewModel.reset() }
                    )
                }
            }
        }
    }
}
