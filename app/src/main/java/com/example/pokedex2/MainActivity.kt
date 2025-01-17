package com.example.pokedex2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.ui.MenuBar.MenuBar
import com.example.pokedex2.ui.Navigation.NavGraph
import com.example.pokedex2.ui.Navigation.NavGraph2
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.ui.TopBar.TopBar
import com.example.pokedex2.viewModel.MenuBarViewModel
import com.example.pokedex2.viewModel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pokedex2Theme {
                val navController = rememberNavController()
                val menuBarViewModel: MenuBarViewModel = viewModel()
                val pokeViewModel: PokeViewModel = hiltViewModel() // Injecting PokeViewModel
                val selectedItemIndex by menuBarViewModel.selectedItemIndex.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            navController = navController
                        )
                    },
                    bottomBar = {
                        MenuBar(
                          viewModel = menuBarViewModel
                        )
                    }
                ) { innerPadding ->
                    when(selectedItemIndex){
                        0 ->  NavGraph(navController = navController,
                            startDestination = "mainPage" )
                        1 ->  NavGraph(
                            navController = navController,
                            startDestination = "favouritePokemon",
                            modifier = Modifier.padding(innerPadding)
                        )
                       // 1 -> PokemonPage(pokemonPageViewModel = PokePageViewModel )
                        2 -> NavGraph2(navController = navController,
                            startDestination = "startingScreenForQuiz")                        //3 -> PokemonDetailScreen(pokeViewModel = viewModel, modifier = Modifier.padding(innerPadding))
                        else -> NavGraph(navController = navController,
                            startDestination = "mainPage")
                    }
                }
            }
        }
    }
}



