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
import com.example.pokedex2.ui.Favorites.ContentFrame
import com.example.pokedex2.ui.Favorites.FavouritePokemonList
import com.example.pokedex2.ui.HomePage.HomePokemonScroll
import com.example.pokedex2.ui.Quiz.Quiz
import com.example.pokedex2.ui.Quiz.StartingScreenForQuiz
import com.example.pokedex2.ui.components.EmptyStateScreen
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.QuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "mainPage",
    modifier: Modifier = Modifier
) {
    val mainPageViewModel: MainPageViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("mainPage") {
            ContentFrame {
                HomePokemonScroll(
                    navController = navController,
                    syncViewModel = hiltViewModel(),
                    fetchAPIViewModel = mainPageViewModel
                )
            }
        }
        composable("favouritePokemon") {
            ContentFrame {
                FavouritePokemonList(
                    favouritesViewModel = hiltViewModel(),
                    syncViewModel = hiltViewModel(),
                    navController = navController
                )
            }
        }
        composable("catchPokemonScreen") {
            ContentFrame {
                EmptyStateScreen(
                    modifier = Modifier
                )
            }
        }
        composable(
            route = "pokemonPage/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            PokemonPage(pokemonIdOrName = pokemonName,
                syncViewModel = hiltViewModel())
        }
    }
}
//navController from startingScreenForQuiz to quiz
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
