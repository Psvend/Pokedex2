package com.example.pokedex2.ui.PokemonList
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokedex2.R
import com.example.pokedex2.ui.SearchAndFilters.FilterOverlay
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.AffirmationViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun HomePokemonScroll(
    viewModel: AffirmationViewModel,
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

    // States for applied filters
    var sunExposure by rememberSaveable { mutableIntStateOf(0) }

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
                    painter = painterResource(R.drawable.bug_image), // Replace with your bug image resource
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
        Column(
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
                            showFilterOverlay = !showFilterOverlay
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Open Filters"
                        )
                    }
            }
            if (showFilterOverlay) {
                FilterOverlay(
                    showOverlay = true,
                    onClose = {showFilterOverlay = false}
                )
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