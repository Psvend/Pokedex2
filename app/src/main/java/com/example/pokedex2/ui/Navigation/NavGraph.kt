package com.example.pokedex2.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedex2.ui.PokemonList.HomePokemonScroll
import com.example.pokedex2.ui.PokemonList.MainPageBackGround
import com.example.pokedex2.viewModel.AffirmationViewModel

//import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") {
            val affirmationViewModel: AffirmationViewModel = hiltViewModel()
            MainPageBackGround(
                viewModel = affirmationViewModel,
                modifier = modifier,
                navController = navController
            )
        }
        composable("pokemonPage") {
            HomePokemonScroll(viewModel = hiltViewModel(), navController = navController)
        }
    }
}