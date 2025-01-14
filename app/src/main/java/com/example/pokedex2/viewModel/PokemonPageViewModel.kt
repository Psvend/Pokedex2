package com.example.pokedex2.viewModel



import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.PokemonSpecies
import com.example.pokedex2.data.remote.json.testPokemon
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokePageViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    //Add here for creating endpoints
    private val _pokemonDetail = MutableStateFlow<testPokemon?>(null)
    val pokemonDetail: StateFlow<testPokemon?> = _pokemonDetail

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _pokemonImage = MutableStateFlow<String?>(null)
    val pokemonImage: StateFlow<String?> = _pokemonImage

    private val _pokemonId = MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId

    private val _pokemonLocations = MutableStateFlow<List<String>>(emptyList())
    val pokemonLocations: StateFlow<List<String>> = _pokemonLocations

    private val _abilities = MutableStateFlow<List<String>>(emptyList())
    val abilities: StateFlow<List<String>> = _abilities

    private val _pokemonSpecies = MutableStateFlow<PokemonSpecies?>(null)
    val pokemonSpecies: StateFlow<PokemonSpecies?> = _pokemonSpecies

    private val _growthRate = MutableStateFlow<String>("Unknown")
    val growthRate: StateFlow<String> = _growthRate


    //Then add it here and then at PokemonPage
    fun fetchPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val detail = pokemonApiService.getPokemonDetail(nameOrId)
                // Set pokemon details
                _pokemonDetail.value = detail

            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch Pokemon details: ${e.message}"
                _pokemonDetail.value = null
                _pokemonImage.value = null

            }
        }
    }


    fun fetchPokemonEncounters(encountersUrl: String) {
        viewModelScope.launch {
            try {
                val encounters = pokemonApiService.getPokemonEncounters(encountersUrl)
                _pokemonLocations.value =
                    encounters.map { it.location_area.name.capitalizeFirstLetter() }
            } catch (e: Exception) {
                _pokemonLocations.value = listOf("No locations available")
            }
        }
    }


    fun fetchPokemonAbilities(pokemonName: String) {
        viewModelScope.launch {
            try {
                val pokemonDetail = pokemonApiService.getPokemonDetail(pokemonName)
                val abilityNames = pokemonDetail.abilities.map { it.ability.name }

                _abilities.value = abilityNames
            } catch (e: Exception) {
                _abilities.value = listOf("Error fetching abilities")
            }
        }
    }

    fun fetchPokemonSpecies(nameOrId: String) {
        viewModelScope.launch {
            try {
                val species = pokemonApiService.getPokemonSpecies(nameOrId)
                _pokemonSpecies.value = species
                _growthRate.value = species.growth_rate.name // Normalize here
                Log.d("PokemonGrowthRate", "Fetched Growth Rate: $growthRate")
            } catch (e: Exception) {
                _growthRate.value = "unknown"
            }
        }
    }


    fun getGrowthRateProgress(growthRate: String): Float {
        return when (growthRate.trim().lowercase()) {
            "slow" -> 0.2f
            "medium-slow" -> 0.4f
            "medium" -> 0.6f
            "fast" -> 0.8f
            "very fast" -> 1.0f
            else -> 0.0f // Default for unknown rates
        }
    }


    fun getGrowthRateColor(growthRate: String): Color {
        return when (growthRate.trim().lowercase()) {
            "slow" -> Color.Blue // Slow = Blue
            "medium-slow" -> Color.Green // Medium-Slow = Green
            "medium" -> Color.Yellow // Medium = Yellow
            "fast" -> Color(0xFFFFA500) // Fast = Orange
            "very fast" -> Color.Red // Very Fast = Red
            else -> Color.Gray // Default for unknown rates
        }
    }


}



