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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavController
import com.example.pokedex2.ui.theme.MainPageBackGround
import com.example.pokedex2.ui.theme.NavGraph
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
                NavGraph()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = { MenuBar() }
                ) { innerPadding ->
                    //Adding the scrollable pokemon list
                    MainPageBackGround(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pokedex2Theme {
        Scaffold (
            bottomBar = { MenuBar() }
        ) {
            //MainPageBackGround(modifier = Modifier.padding(it))
            PokemonPage(modifier = Modifier.padding(it))
        }
    }
}