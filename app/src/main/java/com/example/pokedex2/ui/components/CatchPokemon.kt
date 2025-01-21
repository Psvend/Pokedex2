package com.example.pokedex2.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Pokemon
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import com.example.pokedex2.ui.HomePage.PokemonTypeIcons
import com.example.pokedex2.viewModel.PokemonTypeColorViewModel
import kotlinx.coroutines.delay

@Composable
fun CatchAnimation(isActive: Boolean) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightFloat = LocalConfiguration.current.screenHeightDp.toFloat()
    val ballOffsetY = remember { Animatable(screenHeight.value) } // Start at the bottom of the screen

    // Animation
    if (isActive) {
        LaunchedEffect(Unit) {
            ballOffsetY.animateTo(
                targetValue = screenHeightFloat / 2, // Center of the screen
                animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Clear screen for animation
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.closed_pokeball),
            contentDescription = "Pokeball",
            modifier = Modifier
                .offset(y = ballOffsetY.value.dp - screenHeight / 2) // Animate the vertical position
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
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pokemon.name.capitalizeFirstLetter().addSpaceAndCapitalize(),
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
                Row {
                    PokemonTypeIcons(
                        types = pokemon.type,
                        modifier = Modifier,
                        fontSize = 14
                    ) { type -> typingColorViewModel.getTypeColor(type) }
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

@Composable
fun SparkleAnimation(isActive: Boolean) {
    val sparkles = ImageBitmap.imageResource(R.drawable.sparkle) // Load the sprite sheet
    val frameWidth = sparkles.width / 6 // Assuming 6 frames per row
    val frameHeight = sparkles.height / 6 // Assuming 6 rows

    var currentFrame by remember { mutableStateOf(0) }

    if (isActive) {
        LaunchedEffect(Unit) {
            while (isActive) {
                currentFrame = (currentFrame + 1) % 32 // Loop through 36 frames
                delay(100) // Adjust delay to control frame speed
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val row = currentFrame / 8
        val col = currentFrame % 4

        // Calculate source rectangle
        val srcRect = Rect(
            left = (col * frameWidth).toFloat(),
            top = (row * frameHeight).toFloat(),
            right = ((col + 1) * frameWidth).toFloat(),
            bottom = ((row + 1) * frameHeight).toFloat()
        )

        // Calculate destination rectangle (centered sparkle)
        val scaleFactor = 3.0f // Scale factor for size
        val dstLeft = size.width / 2 - frameWidth * scaleFactor / 2
        val dstTop = size.height / 2 - frameHeight * scaleFactor / 2
        val dstRight = size.width / 2 + frameWidth * scaleFactor / 2
        val dstBottom = size.height / 2 + frameHeight * scaleFactor / 2

        // Draw the specific frame using low-level Canvas API
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawBitmap(
                sparkles.asAndroidBitmap(), // Convert ImageBitmap to Android Bitmap
                android.graphics.Rect(
                    srcRect.left.toInt(),
                    srcRect.top.toInt(),
                    srcRect.right.toInt(),
                    srcRect.bottom.toInt()
                ),
                android.graphics.Rect(
                    dstLeft.toInt(),
                    dstTop.toInt(),
                    dstRight.toInt(),
                    dstBottom.toInt()
                ),
                null
            )
        }
    }
}
