package com.example.pokedex2.ui.PokePage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.data.remote.EvolutionDetailUI
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.HomePage.PokemonTypeIcons
import com.example.pokedex2.ui.PokemonPage.PokemonAbilities
import com.example.pokedex2.ui.PokemonPage.PokemonDescription
import com.example.pokedex2.ui.PokemonPage.PokemonEvolvesTo
import com.example.pokedex2.ui.PokemonPage.PokemonGrowthRate
import com.example.pokedex2.ui.PokemonPage.PokemonImage
import com.example.pokedex2.ui.PokemonPage.PokemonLocation
import com.example.pokedex2.ui.PokemonPage.PokemonName
import com.example.pokedex2.ui.PokemonPage.PokemonNr
import com.example.pokedex2.ui.PokemonPage.PokemonStatsGraph
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import com.example.pokedex2.viewModel.MainPageViewModel
import com.example.pokedex2.viewModel.PrimaryViewModel
import com.example.pokedex2.viewModel.PokemonPageViewModel
import com.example.pokedex2.viewModel.PokemonTypeColorViewModel
import com.example.pokedex2.viewModel.SyncViewModel


@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    modifier: Modifier = Modifier,
    viewModel: PokemonPageViewModel = hiltViewModel(),
    pokePageViewModel: PrimaryViewModel = hiltViewModel(),
    syncViewModel: SyncViewModel = hiltViewModel(),
    fetchAPIViewModel: MainPageViewModel = hiltViewModel(),
    typingColorViewModel: PokemonTypeColorViewModel = viewModel()
) {
    //val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonLocations by viewModel.pokemonLocations.collectAsState()
    val abilities by viewModel.abilities.collectAsState()
    val growthRate by viewModel.growthRate.collectAsState()
    val evolvesTo by viewModel.evolvesTo.collectAsState()
    val stats by viewModel.pokemonStats.collectAsState()
    val characteristicDescription by viewModel.characteristicDescription.collectAsState()
    val likedPokemons by syncViewModel.pokemonList.collectAsState()
    //val isLiked = likedPokemons.any { it.id == pokemonDetail?.id }
    val apiPokemons by fetchAPIViewModel.apiPokemons.collectAsState(initial = emptyList())
    val syncedPokemons by syncViewModel.pokemonList.collectAsState()
    val pokemonDetail by pokePageViewModel.pokemonDetail.collectAsState()
    val pokemonDetailList by pokePageViewModel.pokemonDetailList.collectAsState()

    // Map the evolution data from your API into EvolutionDetailUI objects
    val evolutionDetailsUI = evolvesTo.map { evolution ->
        EvolutionDetailUI(
            name = evolution.name.capitalizeFirstLetter().addSpaceAndCapitalize(),
            imageUrl = evolution.imageUrl, // URL to the Pokémon's sprite
            requirement = evolution.requirement // "Level 22" or "High Friendship"
        )
    }



    LaunchedEffect (pokemonDetailList) {
        pokePageViewModel.getAllPokemon()
    }

    LaunchedEffect(pokemonIdOrName) {
        pokePageViewModel.fetchCachedPokemon(pokemonIdOrName)
    }

    val affirmation = pokemonDetail?.let { pokePageViewModel.convertSingleAffirmation(it) }



    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonAbilities(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonSpecies(pokemonIdOrName.lowercase())
        viewModel.fetchEvolutionChain(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonStats(pokemonIdOrName.lowercase())
    }

    //Fetch pokemon description
    LaunchedEffect(pokemonDetail?.id) {
        pokemonDetail?.id?.let { id ->
            viewModel.fetchCharacteristic(id)
        }
    }

    /*
    // Fetch encounter locations
    LaunchedEffect(pokemonDetail?.location_area_encounters) {
        pokemonDetail?.location_area_encounters?.let { url ->
            viewModel.fetchPokemonEncounters(url)
        }
    }

     */

    Box(
        modifier = Modifier
            .background(Color(0xffeae6d5)) // Set the full background color here
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 25.dp)
        ) {

            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PokemonName(name = pokemonDetail?.name ?: "Unknown")

                pokemonDetail?.id?.let { id ->
                    PokemonNr(id = id)
                }


            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth() // Ensure the spacer spans the width of the column
                    .height(12.dp) // Adjust the height to control the space
                    .background(Color.Transparent) //To test if its being added
            )




            if (affirmation != null) {
                    PokemonImage(
                        model = affirmation.imageResourceId,
                        pokePageViewModel = pokePageViewModel,
                        affirmation = affirmation
                    )
                }

            if (affirmation != null) {
                PokemonTypeIcons(types = affirmation.typeIcon, modifier = Modifier,fontSize = 10, getTypeColor = {type -> typingColorViewModel.getTypeColor(type)})
            }



            Spacer(modifier = Modifier.height(20.dp))

            PokemonDescription(description = characteristicDescription)

            Spacer(modifier = Modifier.height(15.dp))

            PokemonAbilities(abilities = abilities)

            Spacer(modifier = Modifier.height(15.dp))

            PokemonGrowthRate(growthRate = growthRate, viewModel = viewModel)

            Spacer(modifier = Modifier.height(15.dp))

            PokemonEvolvesTo(
                evolvesTo = evolutionDetailsUI,
                currentPokemon = EvolutionDetailUI(
                    name = pokemonDetail?.name?.capitalizeFirstLetter()?.addSpaceAndCapitalize() ?: "Unknown",
                    imageUrl = pokemonDetail?.imageResourceId ?: "",
                    requirement = "This is the final form"
                )
            )


            Spacer(modifier = Modifier.height(15.dp))

            PokemonStatsGraph(stats = stats, viewModel = viewModel)

            Spacer(modifier = Modifier.height(15.dp))


            PokemonLocation(locations = pokemonLocations)


            Spacer(
                modifier = Modifier
                    .fillMaxWidth() // Ensure the spacer spans the width of the column
                    .height(200.dp) // Adjust the height to control the space
                    .background(Color.Transparent) //To test
            )

        }
    }
}