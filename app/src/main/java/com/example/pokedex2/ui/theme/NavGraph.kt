package com.example.pokedex2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.viewModel.AffirmationViewModel
import com.example.pokedex2.viewModel.PokeViewModel
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