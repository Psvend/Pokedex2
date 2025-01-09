package com.example.pokedex2.ui.PokePage


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex2.viewModel.PokePageViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.example.pokedex2.model.Affirmation


@Composable
fun PokemonPage(affirmation: Affirmation, modifier: Modifier = Modifier) {
    //Add new items here to show
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonImage by viewModel.pokemonImage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Top Section - Name and Number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName()
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

    /*
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

     */

        Spacer(modifier = Modifier.height(16.dp))

        // Description Section
        //PokemonDescription(affirmation = pokemonAffirmation)

        Spacer(modifier = Modifier.height(16.dp))

        //Graph section
        //PokemonStats(affirmation = pokemonAffirmation)


    }
}



@Composable
fun PokemonName() {
    Text(
        text = pokemonDetail?.name ?: "Loading...",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Black
    )
}



@Composable
fun PokemonImage () {
    AsyncImage(
        model = pokemonDetail?.sprites?.front_default,
        contentDescription = "{pokemonDetail?.name} sprite",
        modifier = Modifier.size(500.dp)
    )
}





/*

@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    viewModel: PokePageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    //Add new items here to show
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonImage by viewModel.pokemonImage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }


    //UI box to handle name  from api
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(120.dp),
        contentAlignment = Alignment.TopStart
    ) {
        when {
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            pokemonDetail != null -> {
                //Show the name of the pokemon from api
                Text(
                    text = pokemonDetail?.name ?: "Loading...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }

            else -> {
                CircularProgressIndicator()
            }

        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // UI box to handle image
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            pokemonDetail != null -> {
                //Show image from api
                AsyncImage(
                    model = pokemonDetail?.sprites?.front_default,
                    contentDescription = "{pokemonDetail?.name} sprite",
                    modifier = Modifier.size(500.dp)
                )
            }

            else -> {
                CircularProgressIndicator()
            }
        }
    }


}


 */













