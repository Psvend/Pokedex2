package com.example.pokedex2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") { MainPageBackGround(modifier = Modifier, navController ) }
        composable("pokemonPage") { PokemonPage() }
        }

        //composable("MainPageBackGround") {  MainPageBackGround(navController, modifier = Modifier.padding(it)) }
        //composable("PlaceHolder") { Placeholder(navController)}
    }