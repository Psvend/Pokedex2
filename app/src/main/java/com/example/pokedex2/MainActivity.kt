package com.example.pokedex2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex2.ui.theme.MenuBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.ui.theme.MainPageBackGround
import com.example.pokedex2.ui.components.TypeFilterUI
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.ui.theme.PokemonPage

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
                var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MenuBar(
                            selectedItemIndex = selectedItemIndex,
                            onItemSelected = { index -> selectedItemIndex = index }
                        )
                    }
                ) { innerPadding ->
                    when (selectedItemIndex) {
                        0 -> MainPageBackGround(viewModel = viewModel(), modifier = Modifier.padding(innerPadding)) // Home view
                        //1 -> FavoritesView(modifier = Modifier.padding(innerPadding))    // Favorites view
                        2 -> TypeFilterUI(modifier = Modifier.padding(innerPadding))     // Search view
                        //3 -> FilterView(modifier = Modifier.padding(innerPadding))       // Filter view
                        else -> MainPageBackGround(viewModel = viewModel(), modifier = Modifier.padding(innerPadding)) // Default to Home
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pokedex2Theme {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {

                MenuBar(
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index -> selectedItemIndex = index }
                )
            }
        ) {
            //MainPageBackGround(modifier = Modifier.padding(it))
            PokemonPage(modifier = Modifier.padding(it))
            // Conditionally display content based on the selected item
            when (selectedItemIndex) {
                0 -> MainPageBackGround(viewModel = viewModel(), modifier = Modifier.padding()) // Home view
                //1 -> FavoritesView(modifier = Modifier.padding(it))    // Favorites view
                2 -> TypeFilterUI(modifier = Modifier.padding(it))     // Search view
                //3 -> FilterView(modifier = Modifier.padding(it))       // Filter view
                else -> MainPageBackGround(viewModel = viewModel(),modifier = Modifier.padding()) // Default to Home
            }
        }
    }
}
