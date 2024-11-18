package com.example.pokedex2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(startDestination: String = "MainPageBackGround") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") { MainPageBackGround(viewModel = viewModel(), modifier = Modifier) }

        //composable("MainPageBackGround") {  MainPageBackGround(navController, modifier = Modifier.padding(it)) }
        //composable("PlaceHolder") { Placeholder(navController)}
    }
}