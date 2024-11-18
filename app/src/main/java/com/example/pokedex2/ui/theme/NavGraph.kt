package com.example.pokedex2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun NavGraph(navController: NavHostController,
             startDestination: String,
             modifier: Modifier = Modifier,
             pokemonPageViewModel: PokemonPageViewModel

             ) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") { MainPageBackGround(viewModel = viewModel(),modifier = Modifier, navController ) }
        composable("pokemonPage") { PokemonPage(pokemonAffirmation, modifier) }
        }

        //composable("MainPageBackGround") {  MainPageBackGround(navController, modifier = Modifier.padding(it)) }
        //composable("PlaceHolder") { Placeholder(navController)}
    }