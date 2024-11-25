package com.example.pokedex2.ui.theme
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.AffirmationViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.viewModel.Pokemon

@Composable
fun AffirmationsList(
    viewModel: AffirmationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val affirmationList by viewModel.affirmations.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFD9D9D9))
    ) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                navController = navController,
                onLikeClicked = { viewModel.toggleLike(affirmation) },
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}


@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(affirmation.isLiked) }

    Card(
        modifier = modifier.padding(4.dp)
            .clickable { navController.navigate("pokemonPage") },
        colors = CardDefaults.cardColors(Color(0xFFFFF9E6)),
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            // Pokemon image
            Image(
                painter = rememberAsyncImagePainter(affirmation.imageResourceId),
                contentDescription = affirmation.name,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            // Text and category icons in the middle
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                // Name of pokemon
                Text(
                    text = affirmation.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                // Row for dynamic pokemon type text
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    // Our category icons
                    affirmation.typeIcon.forEach { type ->
                        Text(
                            text = type,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }

            // The like button and number
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
                // Id number of pokemon
                Text(
                    text = affirmation.number.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}