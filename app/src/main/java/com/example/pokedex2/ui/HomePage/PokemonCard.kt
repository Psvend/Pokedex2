package com.example.pokedex2.ui.HomePage

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import com.example.pokedex2.viewModel.PokemonTypeColorViewModel


@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: () -> Unit, // Pass a callback with the new state
    navController: NavHostController,
    modifier: Modifier = Modifier,
    typingColorViewModel: PokemonTypeColorViewModel = viewModel()
) {


    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                navController.navigate("pokemonPage/${affirmation.name.lowercase()}")
            },
        colors = CardDefaults.cardColors(Color(0xFFFFF9E6)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(affirmation.imageResourceId),
                contentDescription = affirmation.name,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = affirmation.name, fontSize = 25.sp)
                PokemonTypeIcons(types = affirmation.typeIcon, modifier = Modifier, fontSize = 10) {
                        type -> typingColorViewModel.getTypeColor(type)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = { onLikeClicked() }) {
                    Icon(
                        painter = painterResource(
                            if (affirmation.isLiked) R.drawable.heart_filled else R.drawable.heart_empty
                        ),
                        contentDescription = if (affirmation.isLiked) "Unlike" else "Like",
                        tint = if (affirmation.isLiked) Color(0xFFB11014) else Color(0xFFB11014)
                    )
                }
                Text(
                    text = "#" + affirmation.number.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
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
                    text = type.capitalizeFirstLetter(),
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


