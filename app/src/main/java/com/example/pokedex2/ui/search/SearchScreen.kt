package com.example.pokedex2.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.viewModel.SearchViewModel


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .background(Color(0xFF000000).copy(alpha = 0.95F))
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black)
        ) {
            SearchBarComponent(viewModel)
            Text(
                text = "Choose type filter",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
            TypeSelectionGrid(
                viewModel = viewModel,
                modifier = Modifier
                    //.weight(1f) // Makes it fills the remaining space
                    .padding(12.dp, 0.dp, 12.dp, 16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                ActionButtons(
                    viewModel,
                    modifier = Modifier
                        .border(1.dp, Color.White, RoundedCornerShape(30.dp))
                )

            }
        }
    }
    ErrorDialog(viewModel)
}

