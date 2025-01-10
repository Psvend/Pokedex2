package com.example.pokedex2.ui.PokemonList
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.MainPageViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.utils.RotatingLoader
import kotlinx.coroutines.flow.filter

@Composable
fun HomePokemonScroll(
    viewModel: MainPageViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val affirmationList by viewModel.affirmations.collectAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.value
    val isPaginating = viewModel.isPaginating.value
    val errorMessage = viewModel.errorMessage.value
    val listState = rememberLazyListState()
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showFilterOverlay by remember {mutableStateOf(false)}

    if (isLoading && affirmationList.isEmpty()) {
        // Show a loading spinner during initial load
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            RotatingLoader()
        }
    } else if (errorMessage != null && affirmationList.isEmpty()) {
        // Show an error message, an image of a bug, and a refresh button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                // Error image
                Image(
                    painter = painterResource(R.drawable.bug_image), // Bug image when no internet
                    contentDescription = "Error",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Error message
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Refresh button
                Button(
                    onClick = { viewModel.fetchAffirmations(0) } // Retry fetching data
                ) {
                    Text("Retry")
                }
            }
        }
    } else {
        // Show the Pokémon list
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(affirmationList) { affirmation ->
                AffirmationCard(
                    affirmation = affirmation,
                    navController = navController,
                    onLikeClicked = {
                        /*
                        viewModel.toggleLike(
                            context = LocalContext.current,
                            affirmation = affirmation
                        )

                         */
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }

            if (isPaginating) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                       CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }

        // Detect when the user scrolls to the bottom
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .filter { it == affirmationList.size - 1 && !isPaginating && !isLoading }
                .collect {
                    viewModel.loadNextPage() // Use the helper method
                }
        }
    }
}

@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: @Composable (Affirmation) -> Unit, // Composable callback
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
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
            // Pokémon image
            Image(
                painter = rememberAsyncImagePainter(affirmation.imageResourceId),
                contentDescription = affirmation.name,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            // Text and category icons
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                // Pokémon name
                Text(
                    text = affirmation.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                // Pokémon types
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    affirmation.typeIcon.forEach { type ->
                        Text(
                            text = type,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            // Like button and Pokémon ID
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                // Heart icon for like/unlike
                onLikeClicked(affirmation) // Invoke the composable function

                Text(
                    text = "#" + affirmation.number.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/*
*
//Creates the boxes around each type
@Composable
fun PokemonTypeIcons(types: List<String>, modifier: Modifier = Modifier) {
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
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
    }
}

*
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

* */