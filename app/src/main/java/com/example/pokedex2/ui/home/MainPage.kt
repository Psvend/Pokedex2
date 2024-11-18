package com.example.pokedex2.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
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
                    //.padding(top = 80.dp)
                ) {
            //Edit header spacing
            //Spacer(modifier = Modifier.height(60.dp))

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

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pokedex2Theme {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {

                MenuBar(
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index -> selectedItemIndex = index }
                )
            }
            topBar = {
                TopBar()
            }
        ) {
            MainPageBackGround(modifier = Modifier.padding(it))
            PokemonPage(modifier = Modifier.padding(it))
            // Conditionally display content based on the selected item
            when (selectedItemIndex) {
                0 -> MainPageBackGround(modifier = Modifier.padding()) // Home view
                //1 -> FavoritesView(modifier = Modifier.padding(it))    // Favorites view
                2 -> TypeFilterUI(modifier = Modifier.padding(it))     // Search view
                //3 -> FilterView(modifier = Modifier.padding(it))       // Filter view
                else -> MainPageBackGround(modifier = Modifier.padding()) // Default to Home
            }
        }
    }
}*/

