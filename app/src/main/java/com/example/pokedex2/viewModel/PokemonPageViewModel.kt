package com.example.pokedex2.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.data.remote.PokemonApiService
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

    //private val _pokemonHabitat = MutableStateFlow<String?>(null)
    //val pokemonHabitat: StateFlow<String?> = _pokemonHabitat

    private val _pokemonLocations = MutableStateFlow<List<String>>(emptyList())
    val pokemonLocations: StateFlow<List<String>> = _pokemonLocations

    //private val _pokemonForms = MutableStateFlow<List<String>>(emptyList())
    //val pokemonForms: StateFlow<List<String>> = _pokemonForms


    //Then add it here and then at PokemonPage
    fun fetchPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val detail = pokemonApiService.getPokemonDetail(nameOrId)
                //val habitat = pokemonApiService.

                // Set pokemon details
                _pokemonDetail.value = detail

                //fetch location
                //val locationEncounters = detail.location_area_encounters
                //_pokemonLocation.value = null

            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch Pokemon details: ${e.message}"
                _pokemonDetail.value = null
                _pokemonImage.value = null
                //_pokemonLocation.value = null
            }
        }
    }


    fun fetchPokemonEncounters(encountersUrl: String) {
        viewModelScope.launch {
            try {
                val encounters = pokemonApiService.getPokemonEncounters(encountersUrl)
                _pokemonLocations.value = encounters.map { it.location_area.name.capitalizeFirstLetter() }
            } catch (e: Exception) {
                _pokemonLocations.value = listOf("No locations available")
            }
        }
    }

/*
    fun fetchPokemonForms(urls: List<String>) {
        viewModelScope.launch {
            try {
                val formsNames = urls.map { url ->
                    val formsResponse = pokemonApiService.getPokemonForms(url)
                    formsResponse.pokemon_forms.map { it.name }
                }.flatten()
                _pokemonForms.value = formsNames
            } catch (e: Exception) {
                _pokemonForms.value = listOf("No forms available")
            }
        }
    }

 */




}



