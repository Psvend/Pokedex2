package com.example.pokedex2.ui.PokemonList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.example.pokedex2.viewModel.AffirmationViewModel
import androidx.compose.runtime.*


@Composable
fun MainPageBackGround(
    viewModel: AffirmationViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val affirmationList by viewModel.affirmations.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD9D9D9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(65.dp))

            val layoutDirection = LocalLayoutDirection.current
            Surface(
                modifier = modifier
                    .statusBarsPadding()
                    .padding(
                        start = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                    )
            ) {
                HomePokemonScroll(
                    viewModel,
                    modifier = modifier,
                    navController = navController,
                )
            }
        }
    }
}
