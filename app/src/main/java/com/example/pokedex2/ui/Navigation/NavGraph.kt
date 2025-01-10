package com.example.pokedex2.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedex2.ui.PokePage.PokemonPage
import com.example.pokedex2.ui.PokemonList.FavouritePokemonList
import com.example.pokedex2.ui.PokemonList.MainPageBackGround
import com.example.pokedex2.viewModel.MainPageViewModel

//import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "mainPage",
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") {
            val mainPageViewModel: MainPageViewModel = hiltViewModel()
            MainPageBackGround(
                viewModel = mainPageViewModel,
                modifier = modifier,
                navController = navController
            )
        }
        composable(
            route = "pokemonPage/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            PokemonPage(pokemonIdOrName = pokemonName)
        }
        composable("favouritePokemon") {
            FavouritePokemonList(viewModel = viewModel(), navController = navController)
        }
    }
}