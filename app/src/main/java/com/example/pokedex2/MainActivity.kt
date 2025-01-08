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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.ui.SearchAndFilters.TypeFilterUI
import com.example.pokedex2.ui.MenuBar.MenuBar
import com.example.pokedex2.ui.Navigation.NavGraph
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.ui.TopBar.TopBar
//import com.example.pokedex2.ui.theme.PokemonDetailScreen
//import com.example.pokedex2.ui.theme.PokemonPage2
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.MenuBarViewModel
import com.example.pokedex2.viewModel.TopBarViewModel
//import com.example.pokedex2.viewModel.PokemonPageViewModel
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
                val topBarViewModel: TopBarViewModel = viewModel()
                val menuBarViewModel: MenuBarViewModel = viewModel()
                val selectedItemIndex by menuBarViewModel.selectedItemIndex.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            viewModel = topBarViewModel,
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
                        //1 -> PokemonPage2(pokemonPageViewModel )
                        2 -> TypeFilterUI(modifier = Modifier.padding(innerPadding))
                        //3 -> PokemonDetailScreen(pokeViewModel = viewModel, modifier = Modifier.padding(innerPadding))
                        else -> RotatingLoader()
                    }
                }
            }
        }
    }
}

