package com.example.pokedex2.ui.HomePage

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.PokemonTypeColorViewModel
import com.example.pokedex2.viewModel.SyncViewModel


@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    typingColorViewModel: PokemonTypeColorViewModel = viewModel(),
    mainPageViewModel: MainPageViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
){


    val pokemonLikedList by mainPageViewModel.pokemonLikedList.collectAsState()



    LaunchedEffect (pokemonLikedList) {
        mainPageViewModel.getAllLikedPokemons()
        Log.d("Jeg er en kælling kælling","${affirmation.name} ${affirmation.isLiked}")

    }

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
            ) {
                Text(
                    text = affirmation.name.addSpaceAndCapitalize(),
                    fontSize = 25.sp
                )
                PokemonTypeIcons(types = affirmation.typeIcon, modifier = Modifier,fontSize = 9) { type ->
                    typingColorViewModel.getTypeColor(
                        type
                    )
                }
            }
            // Like button and ID
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = {onLikeClicked()
                affirmation.isLiked = !affirmation.isLiked} )
            {
                    Icon(
                        imageVector = if (affirmation.isLiked) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = if (affirmation.isLiked) "Toggled Icon" else "Default Icon",
                        tint = Color(0xFFB11014),
                        modifier = Modifier
                            .size(35.dp)

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

@Composable
fun PokemonTypeIcons(
    types: List<String>,
    modifier: Modifier = Modifier,
    fontSize: Int,
    getTypeColor: (String) -> Color
    ) {
    Row(
        modifier = modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        types.forEach { type ->
            Box(
                modifier = Modifier
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        color = getTypeColor(type),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type.capitalizeFirstLetter().addSpaceAndCapitalize(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.pressstart2p_regular)),
                        shadow = Shadow(
                            color = Color.Black, offset = Offset(0.0f, 0.0f), blurRadius = 10f
                        ),
                        fontSize = fontSize.sp),
                    color = Color.White
                )
            }
        }
    }
}


