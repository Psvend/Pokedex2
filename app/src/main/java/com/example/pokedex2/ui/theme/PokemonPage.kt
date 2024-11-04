package com.example.pokedex2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PokemonPage(modifier: Modifier = Modifier) {
    Row (modifier = modifier.fillMaxSize()){
        PokemonName(name = "Pikachu", modifier = Modifier)
        PokemonNr(nr = 25, modifier = Modifier)

    }
    Box(modifier = modifier.fillMaxSize()) {
        //backgroundImage(url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", modifier = Modifier)
        PokemonImage(url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", modifier = Modifier)
        PokemonType(type = "Electric", modifier = Modifier)
        PokemonType2(type = "Electric", modifier = Modifier)
        PokemonDescription(description = "This intelligent Pokémon roasts hard berries with electricity to make them tender enough to eat.", modifier = Modifier)
        //PokemonStats(url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", modifier = Modifier)
        LikeButton()
    }

}

@Composable
fun backgroundImage(url: String, modifier: Modifier) {
    AsyncImage(
        model = url,
        contentDescription = "background",
        contentScale = ContentScale.FillBounds,
        modifier = modifier.size(
            width = 411.dp,
            height = 913.dp
        )
    )


}
@Composable
fun PokemonName(name : String, modifier: Modifier){
    val textModifer = modifier.offset(x = 100.dp, y = 65.dp)
    Text(
        text = name,
        modifier = textModifer,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    )
}
@Composable
fun PokemonNr(nr : Int, modifier: Modifier){
    val textModifer = modifier.offset(x = 100.dp, y = 65.dp)
    Text(
        text = "#$nr",
        modifier = textModifer,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    )
}
@Composable
fun PokemonImage(url: String, modifier: Modifier){
    val boxModifier = modifier.offset(x = 100.dp, y = 100.dp)
    Box(boxModifier) {
        AsyncImage(
            model = url,
            contentDescription = "Pokemon",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.size(
                width = 200.dp,
                height = 200.dp
            )
        )
    }

}
@Composable
fun PokemonType(type: String, modifier: Modifier){
    val textModifer = modifier.offset(x = 10.dp, y = 370.dp)
    Text(
        text = type,
        modifier = textModifer,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    )
}
@Composable
fun PokemonType2(type: String, modifier: Modifier){
    val textModifer = modifier.offset(x = 110.dp, y = 370.dp)
    Text(
        text = type,
        modifier = textModifer,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    )
}
@Composable
fun PokemonDescription(description: String, modifier: Modifier){
    val textModifer = modifier.offset(x = 10.dp, y = 400.dp)
    Text(
        text = description,
        modifier = textModifer,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight =  FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    )
}
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
fun togleLikeState(currentState: Boolean): Boolean {
    return !currentState

}
@Composable
fun LikeButton(){
    var isSelect by remember { mutableStateOf(false) }
    val boxModifier = Modifier.offset(x = 270.dp, y = 100.dp)
    val imageModifierLike = Modifier
        .size(width = 70.dp, height = 70.dp)
    Box(
        modifier = boxModifier.clickable {
            isSelect = togleLikeState(isSelect) }
    ){
        if (isSelect)
            Image(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Like",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifierLike
            )
         else
            Image(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "unLike",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifierLike
            )

    }


}