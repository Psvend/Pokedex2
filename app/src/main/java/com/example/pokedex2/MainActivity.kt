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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.ui.topbar.TopBar
import com.example.pokedex2.ui.search.SearchScreen
import com.example.pokedex2.ui.menuNav.MenuBar
import com.example.pokedex2.ui.menuNav.NavGraph
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.ui.util.RotatingLoader
import com.example.pokedex2.viewModel.MenuBarViewModel
import com.example.pokedex2.viewModel.PokemonPageViewModel
import com.example.pokedex2.viewModel.TopBarViewModel


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pokedex2Theme {
                val navController = rememberNavController()
                val menuBarViewModel: MenuBarViewModel = viewModel()
                val topBarViewModel: TopBarViewModel = viewModel()
                val pokemonPageViewModel: PokemonPageViewModel = viewModel()
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
                            startDestination = "mainPage" , pokemonPageViewModel = pokemonPageViewModel,
                            modifier = Modifier.padding(innerPadding))
                        //1 -> FavoritesView(modifier = Modifier.padding(it))
                        2 -> SearchScreen()
                        //3 -> FilterScreen()
                        else -> RotatingLoader()
                    }
                }
            }
        }
    }
}