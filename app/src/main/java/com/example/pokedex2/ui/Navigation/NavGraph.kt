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
import com.example.pokedex2.ui.PokemonList.HomePokemonScroll
import com.example.pokedex2.ui.PokemonList.MainPageBackGround
import com.example.pokedex2.ui.Quiz.Quiz
import com.example.pokedex2.ui.Quiz.StartingScreenForQuiz
import com.example.pokedex2.viewModel.AffirmationViewModel
import com.example.pokedex2.viewModel.PokePageViewModel
import com.example.pokedex2.viewModel.QuizViewModel

//import com.example.pokedex2.viewModel.PokemonPageViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "mainPage",
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
        composable(
            route = "pokemonPage/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            PokemonPage(pokemonIdOrName = pokemonName)
        }



    }
}
//navcontroller from startingscreenforquiz to quiz
@Composable
fun NavGraph2(
    navController: NavHostController,
    startDestination: String = "startingScreenForQuiz",
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("startingScreenForQuiz") {
            StartingScreenForQuiz(navController = navController)
        }
        composable("Quiz") {
            val quizViewModel: QuizViewModel = hiltViewModel()
            Quiz(viewModel = quizViewModel)
        }
    }
}
