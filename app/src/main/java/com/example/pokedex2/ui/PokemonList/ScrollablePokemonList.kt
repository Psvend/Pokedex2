package com.example.pokedex2.ui.PokemonList
import com.example.pokedex2.viewModel.AllPokemonsViewModel
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.RectangleShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.SyncViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun HomePokemonScroll(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    syncViewModel: SyncViewModel = hiltViewModel(),
    fetchAPIViewModel: MainPageViewModel = hiltViewModel()
) {
    val isLoading by fetchAPIViewModel.isLoading.collectAsState()
    val isPaginating by fetchAPIViewModel.isPaginating.collectAsState()
    val errorMessage by fetchAPIViewModel.errorMessage.collectAsState()
    val listState = rememberLazyListState()
    val apiPokemons by fetchAPIViewModel.apiPokemons.collectAsState(initial = emptyList())
    val syncedPokemons by syncViewModel.pokemonList.collectAsState(initial = emptyList())

    LaunchedEffect(apiPokemons) {
        syncViewModel.syncPokemons(apiPokemons)
    }

    if (isLoading && syncedPokemons.isEmpty()) {
        // Show loading spinner
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            RotatingLoader()
        }
    } else if (errorMessage != null && syncedPokemons.isEmpty()) {
        // Show error state
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
                Image(
                    painter = painterResource(R.drawable.bug_image),
                    contentDescription = "Error",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage!!,
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily(Font(R.font.pressstart2p_regular))),
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { fetchAPIViewModel.loadNextPage() }) {
                    Text("Retry")
                }
            }
        }
    } else {
        // Show Pokémon list
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            items(syncedPokemons) { affirmation ->
                AffirmationCard(
                    affirmation = affirmation,
                    navController = navController,
                    onLikeClicked = { syncViewModel.toggleLike(affirmation) },
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
                        /*Create another overlay loader*/
                        RotatingLoader()
                    }
                }
            }
        }

        // Detect scroll to bottom
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .filter { it == syncedPokemons.size - 1 && !isPaginating && !isLoading }
                .collect {
                    fetchAPIViewModel.loadNextPage()
                }
        }
    }
}

/*
@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    onLikeClicked: () -> Unit,
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
            // Text and type icons
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = affirmation.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                PokemonTypeIcons(types = affirmation.typeIcon)
            }

            // Like button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                IconButton(onClick = onLikeClicked) {
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
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

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
        "dragon" -> Color(0xff11ddd6) // Light Blue
        "dark" -> Color(0xff3f4948) // Dark Gray
        "ghost" -> Color(0xff6a8180) // Muddy green
        "rock" -> Color(0xff908065) // Sand brown
        else -> Color.Gray // Default Gray
    }
}

 */


/*
*   Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                        .background(Color.White, shape = RoundedCornerShape(25.dp)),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                        )
                    },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon",
                                modifier = Modifier.clickable { searchQuery = "" }
                            )
                        }
                    } else null,
                    singleLine = true,
                    shape = RoundedCornerShape(25.dp)
                )

                    IconButton(
                        onClick = {
                            if(showFilterOverlay){
                                showFilterOverlay = false
                            } else {
                                showFilterOverlay = true
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Open Filters"
                        )
                    }
            }

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
                        onLikeClicked = { viewModel.toggleLike(affirmation) },
                        modifier = Modifier
                            .padding(4.dp)
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
                            RotatingLoader()
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
}


* */