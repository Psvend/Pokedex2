package com.example.pokedex2.ui.theme

import androidx.compose.foundation.Image
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.R
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokedex2.data.DatasourcePokemon


//Our now specific pokemon, can be changed by nav and API later on
val datasource = DatasourcePokemon.loadAffirmations()
val bulbasaurAffirmation = datasource[0]
val pokemonAffirmation = bulbasaurAffirmation


@Composable
fun PokemonPage(affirmation: Affirmation, modifier: Modifier = Modifier) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_bar_background),
                contentDescription = "Top Bar Background",
                //alpha = 0.2f, hvis opacity skal ændres
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //.align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            )

        }

        Spacer(modifier = Modifier.height(2.dp))

        // Top Section - Name and Number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName(affirmation = pokemonAffirmation)
            PokemonNr(affirmation = pokemonAffirmation)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center Image and Like Button
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PokemonImage(affirmation = pokemonAffirmation)
            LikeButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y=20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        // Types Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp),

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonType(affirmation = pokemonAffirmation)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description Section
        PokemonDescription(affirmation = pokemonAffirmation)


    }
}

@Composable
fun PokemonName(affirmation: Affirmation){
    Text(
        text = stringResource(affirmation.stringResourceId),
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.Default
        ),
        color = Color.DarkGray
    )
}


@Composable
fun PokemonNr(affirmation: Affirmation){
    Text(
        text = affirmation.number,
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.Default
        ),
        color = Color.DarkGray
    )
}


@Composable
fun PokemonImage(affirmation: Affirmation) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = "Pokemon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp, 240.dp)
                    .clip(RoundedCornerShape(12.dp))

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}



@Composable
fun PokemonType(affirmation: Affirmation) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
    ) {

        //Our category icons
        affirmation.typeIcon.forEach { typeIcon ->
            Image(
                    painter = painterResource(typeIcon),
                    contentDescription = stringResource(affirmation.stringResourceId),
                    modifier = Modifier
                        .width(50.dp)
                        .height(20.dp)
                        .padding(2.dp) //space between the categories
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .size(30.dp),
                    contentScale = ContentScale.Crop
            )

        }
    }
}




@Composable
fun PokemonDescription(affirmation: Affirmation){
    Text(
        text = affirmation.description,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Default
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
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
fun LikeButton(modifier : Modifier = Modifier){
    var isSelect by remember { mutableStateOf(false) }
    val iconModifier = Modifier
        .size(40.dp)
        .clickable { isSelect = !isSelect }

    Box(modifier = Modifier) {
        if (isSelect) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Like",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .offset(x=110.dp, y =-85.dp)
                    .clickable { isSelect = !isSelect }
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Unlike",
                tint = Color.Gray,  
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .offset(x=110.dp, y =-85.dp)
                    .clickable { isSelect = !isSelect }
            )
        }
    }
}