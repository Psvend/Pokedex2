package com.example.pokedex2.ui.home

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokedex2.viewModel.AffirmationViewModel


@Composable
fun MainPageBackGround(
    viewModel: AffirmationViewModel,
    modifier: Modifier,
    navController: NavHostController
) { val affirmationList by viewModel.affirmations.collectAsState(initial = emptyList())

    Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp, bottom = 80.dp)
                ) {
            //Edit header spacing
            Spacer(modifier = Modifier.height(60.dp))

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
                AffirmationsList(
                    viewModel,
                    modifier = modifier,
                    navController = navController,
                )
            }
        }
}