package com.example.pokedex2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex2.R
import com.example.pokedex2.data.DatasourcePokemon
import com.example.pokedex2.ui.components.TypeFilterUI

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .height(120.dp)
    ) {
        Box(modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(Color(0xFFE55655).copy(alpha = 0.9f))
                .height(22.dp)
        ){
        }
        Image(
            painter = painterResource(id = R.drawable.top_bar_background),
            contentDescription = "Top Bar Background",
            alpha = 0.9f,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                //.padding(bottom = 8.dp)
                .fillMaxWidth()
            )
        Button(
            onClick = { /* Handle button click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 75.dp, end = 16.dp)
                .size(width = 45.dp, height = 11.dp)
        ) {
            Text("Button")
        }
        Button(
            onClick = { /* Handle button click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 36.dp, start = 16.dp)
                .size(width = 70.dp, height = 70.dp)
        ) {
        }
        }
    }
/*Button(
onClick = { /*expanded = !expanded or navController.navigate("placeholder")
                    */
},
colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
modifier = Modifier
.padding(top = 20.dp, end = 16.dp)
.size(50.dp)
)*/

@Preview
@Composable
fun TopBarView () {
    Pokedex2Theme {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        Scaffold(
            topBar = @androidx.compose.runtime.Composable { TopBar() },

            bottomBar = {
                MenuBar(selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index -> selectedItemIndex = index })
            }
        ) {
            MainPageBackGround(modifier = Modifier.padding(it))
            PokemonPage(modifier = Modifier.padding(it))
            // Conditionally display content based on the selected item
            when (selectedItemIndex) {
                0 -> MainPageBackGround(modifier = Modifier.padding()) // Home view
                //1 -> FavoritesView(modifier = Modifier.padding(it))    // Favorites view
                2 -> TypeFilterUI(modifier = Modifier.padding(it))     // Search view
                //3 -> FilterView(modifier = Modifier.padding(it))       // Filter view
                else -> MainPageBackGround(modifier = Modifier.padding()) // Default to Home
            }
        }

    }
}
