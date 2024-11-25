package com.example.pokedex2.utils

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokedex2.R


@Composable
fun RotatingLoader(
    painter: Painter = painterResource(id = R.drawable.pngwing),
    viewModel: RotatingLoaderViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier
) {
    val rotation by viewModel.rotation // Observe rotation state from ViewModel

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Loading Indicator",
            modifier = Modifier
                .size(125.dp) // Loading pokemon size
                .rotate(rotation)
        )
    }
}