package com.example.pokedex2.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PokemonPage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F1EB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD73A31))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PokéDex",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Top Section - Name and Number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName(name = "Bulbasaur")
            PokemonNr(nr = 1)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center Image and Like Button
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PokemonImage(
                url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
            )
            LikeButton()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Types Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonType(type = "Grass")
            PokemonType(type = "Poison")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description Section
        PokemonDescription(
                description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun’s rays, the seed grows progressively larger."
        )


    }
}

@Composable
fun PokemonName(name : String){
    Text(
        text = name,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        ),
        color = Color.DarkGray
    )
}
@Composable
fun PokemonNr(nr : Int){
    Text(
        text = "#$nr",
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        ),
        color = Color.DarkGray
    )
}
@Composable
fun PokemonImage(url: String){

        AsyncImage(
            model = url,
            contentDescription = "Pokemon",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(200.dp)
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .shadow(4.dp, shape = RoundedCornerShape(8.dp))

        )

}
@Composable
fun PokemonType(type: String){
    Text(
        text = type.uppercase(),
        modifier = Modifier
            .background(
                color = if (type == "Grass") Color(0xFF78C850) else Color(0xFFA040A0),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    )
}

@Composable
fun PokemonDescription(description: String){
    Text(
        text = description,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

/*

@Composable
fun PokemonStats(url: String, modifier: Modifier){
    AsyncImage(
        model = url,
        contentDescription = "Stats",
        contentScale = ContentScale.FillBounds,
        modifier = modifier.size(
            width = 200.dp,
            height = 200.dp
        )
    )
}
 */


@Composable
fun LikeButton(){
    var isSelect by remember { mutableStateOf(false) }
    val iconModifier = Modifier
        .size(40.dp)
        .clickable { isSelect = !isSelect }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isSelect) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Like",
                tint = Color.Red,
                modifier = iconModifier.align(Alignment.TopEnd)
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Unlike",
                tint = Color.Gray,  
                modifier = iconModifier.align(Alignment.TopEnd)
            )
        }
    }
}