package com.example.pokedex2.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedex2.ui.PokePage.PokemonPage
import com.example.pokedex2.ui.PokemonList.FavouritePokemonList
import com.example.pokedex2.ui.PokemonList.ContentFrame
import com.example.pokedex2.ui.PokemonList.HomePokemonScroll
import com.example.pokedex2.viewModel.AllPokemonsViewModel
import com.example.pokedex2.viewModel.MainPageViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "mainPage",
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") {
            val mainPageViewModel: MainPageViewModel = hiltViewModel()
            ContentFrame(modifier = modifier) {
                HomePokemonScroll(
                    navController = navController,
                    syncViewModel = hiltViewModel(),
                    fetchAPIViewModel = mainPageViewModel
                )
            }
        }
        composable(
            route = "pokemonPage/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            ContentFrame(modifier = modifier) {
                PokemonPage(pokemonIdOrName = pokemonName)
            }
        }
        composable("favouritePokemon") {
            ContentFrame(modifier = modifier) {
                FavouritePokemonList(
                    favouritesViewModel = hiltViewModel(),
                    syncViewModel = hiltViewModel(),
                    navController = navController
                )
            }
        }
    }
}
