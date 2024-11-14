package com.example.pokedex2

import com.example.pokedex2.model.Pokemon
import com.example.pokedex2.data.DatasourcePokemon
import androidx.compose.material3.Card
import androidx.compose.foundation.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex2.ui.theme.MenuBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex2.ui.components.TypeFilterUI
import com.example.pokedex2.ui.theme.AffirmationsApp
import com.example.pokedex2.ui.theme.Pokedex2Theme

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
                    // Conditionally render content based on selectedItemIndex
                    when (selectedItemIndex) {
                        0 -> AffirmationsApp(modifier = Modifier.padding(innerPadding)) // Home view
                        //1 -> FavoritesView(modifier = Modifier.padding(innerPadding))    // Favorites view
                        2 -> TypeFilterUI(modifier = Modifier.padding(innerPadding))     // Search view
                        //3 -> FilterView(modifier = Modifier.padding(innerPadding))       // Filter view
                        else -> AffirmationsApp(modifier = Modifier.padding(innerPadding)) // Default to Home
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
        var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                MenuBar(
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index -> selectedItemIndex = index }
                )
            }
        ) {
            // Conditionally display content based on the selected item
            when (selectedItemIndex) {
                0 -> AffirmationsApp(modifier = Modifier.padding(it)) // Home view
                //1 -> FavoritesView(modifier = Modifier.padding(it))    // Favorites view
                2 -> TypeFilterUI(modifier = Modifier.padding(it))     // Search view
                //3 -> FilterView(modifier = Modifier.padding(it))       // Filter view
                else -> AffirmationsApp(modifier = Modifier.padding(it)) // Default to Home
            }
        }
    }
}
