package com.example.pokedex2.ui.HomePage
import android.util.Log
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.R
import com.example.pokedex2.ui.Filters.FilterOverlay
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.FilterViewModel
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.PokePageViewModel
import com.example.pokedex2.viewModel.SyncViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun HomePokemonScroll(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    syncViewModel: SyncViewModel = hiltViewModel(),
    fetchAPIViewModel: MainPageViewModel = hiltViewModel(),
    pokePageViewModel: PokePageViewModel = hiltViewModel(),
    filterViewModel: FilterViewModel = viewModel()
) {
    val isLoading by fetchAPIViewModel.isLoading.collectAsState()
    val isPaginating by fetchAPIViewModel.isPaginating.collectAsState()
    val errorMessage by fetchAPIViewModel.errorMessage.collectAsState()
    val listState = rememberLazyListState()
    val syncedPokemons by syncViewModel.pokemonList.collectAsState(initial = emptyList())
    val pokemonDetail by pokePageViewModel.pokemonDetail.collectAsState()
    val affirmationList = pokePageViewModel.convertToAffirmation(pokemonDetail)
    var showFilterOverlay by remember {mutableStateOf(false)}
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var generations by rememberSaveable {mutableStateOf<ClosedRange<Int>?>(null)}
    var selectedType by rememberSaveable { mutableStateOf("") }

    val filteredAffirmationList = affirmationList.filter { affirmation ->
        val matchesSearch = searchQuery.isBlank() || affirmation.doesMatchQuery(searchQuery)
        val selectedGenerations = filterViewModel.selectionGenMap.filterValues { it }.keys
        val matchesGeneration = selectedGenerations.isEmpty() || selectedGenerations.any { generationId ->
        val generation = filterViewModel.pokeGenerations.value.find { it.id == generationId }
            generation?.range?.contains(affirmation.number) == true
        }
        val matchesTypes = selectedType.isEmpty() || affirmation.typeIcon.contains(selectedType)
        matchesSearch && matchesGeneration && matchesTypes
    }

    if (isLoading && affirmationList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            RotatingLoader()
        }
    } else if (errorMessage != null && syncedPokemons.isEmpty()) {
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
                    style = MaterialTheme.typography.bodyMedium,
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it},
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
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
                    modifier = Modifier
                        .padding(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open Filters",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            if (showFilterOverlay) {
                FilterOverlay(
                    showOverlay = true,
                    onClose = { showFilterOverlay = false },
                    onFilterApply = {typesFilter, generationsFilter ->
                        selectedType = typesFilter
                        generations = generationsFilter
                        showFilterOverlay = false
                        Log.d("HomePokemonScroll", "nummer 3: ${typesFilter.isEmpty()}")
                    }
                )
            } else if (filteredAffirmationList.isEmpty()){
                Column(
                    modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF9E6))
                        .padding(bottom = 170.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier.size(260.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bug_image),
                            contentDescription = "Empty List",
                            modifier.size(240.dp)
                        )
                    }
                    Spacer(modifier.height(16.dp))
                    Text(
                        text = "No Pokémon matching criteria!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )

                    Spacer(modifier.height(16.dp))
                    Button(
                        onClick = {
                            showFilterOverlay = true
                            showFilterOverlay = false
                        },
                        modifier = Modifier.padding(3.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4))
                    ) {
                        Text(
                            text = "Retry",
                            color = Color.White
                        )
                    }
                }
            }
            else {
                LazyColumn(
                    state = listState,
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color(0xFFD9D9D9))
                ) {
                    items(
                        filteredAffirmationList
                    ) { affirmation ->
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
                                RotatingLoader()
                            }
                        }
                    }
                }
                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .filter { it == affirmationList.size - 1 && !isPaginating && !isLoading }
                        .collect {
                            fetchAPIViewModel.loadNextPage()
                        }
                }
            }
        }
    }
}