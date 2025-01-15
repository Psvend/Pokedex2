package com.example.pokedex2.ui.PokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter

@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(affirmation.isLiked) }

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                // Navigate to pokemonPage with the Pokémon name
                navController.navigate("pokemonPage/${affirmation.name.lowercase()}")
            },
        colors = CardDefaults.cardColors(Color(0xFFFFF9E6)),
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            // Pokémon image
            Image(
                painter = rememberAsyncImagePainter(affirmation.imageResourceId),
                contentDescription = affirmation.name,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            // Pokémon name and type
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = affirmation.name,
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = FontFamily(
                        Font(
                            R.font.pressstart2p_regular)
                    ), fontSize = 15.sp)
                )
                PokemonTypeIcons(types = affirmation.typeIcon, fontSize = 6.sp)
            }
            // Like button and ID
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = {
                    isLiked = !isLiked
                    onLikeClicked()
                }) {
                    Icon(
                        painter = painterResource(
                            if (isLiked) R.drawable.heart_filled else R.drawable.heart_empty
                        ),
                        contentDescription = if (isLiked) "Unlike" else "Like",
                        tint = if (isLiked) Color(0xFFB11014) else Color(0xFFB11014)
                    )
                }
                Text(
                    text = "#" + affirmation.number.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily(Font(R.font.pressstart2p_regular)), fontSize = 10.sp),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


//Creates the boxes around each type
@Composable
fun PokemonTypeIcons(types: List<String>, modifier: Modifier = Modifier, fontSize: TextUnit = 10.sp) {
    Row(
        modifier = modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        types.forEach { type ->
            Box(
                modifier = Modifier
                    .background(
                        color = getTypeColor(type),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type.capitalizeFirstLetter(),
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily(Font(R.font.pressstart2p_regular)), fontSize = fontSize),
                    color = Color.White
                )
            }
        }
    }
}


//color boxes for the pokemon types
fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color(0xFFd61717) // Red
        "grass" -> Color(0xFF89de65) // Green
        "water" -> Color(0xFF0000FF) // Blue
        "electric" -> Color(0xFFdddc11) // Yellow
        "bug" -> Color(0xFFa4c81a) // Light Green
        "poison" -> Color(0xFF8E44AD) // Purple
        "ice" -> Color(0xFF00FFFF) // Cyan
        "normal" -> Color(0xfff68d53) // White
        "ground" -> Color(0xFF8B4513) // Brown
        "flying" -> Color(0xFFADD8E6) // Light Blue
        "fairy" -> Color(0xFFEE99AC) // Pink
        "fighting" -> Color(0xFFa41353) // Reddish Brown
        "psychic" -> Color(0xFFFF69B4) // Hot Pink
        "dragon" -> Color(0xff11ddd6)
        "dark" -> Color(0xff3f4948)
        "ghost" -> Color(0xff6a8180)
        "rock" -> Color(0xff908065)
        else -> Color.Gray // Default Gray
    }
}