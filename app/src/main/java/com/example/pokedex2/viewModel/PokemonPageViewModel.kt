package com.example.pokedex2.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.json.testPokemon
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokePageViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<testPokemon?>(null)
    val pokemonDetail: StateFlow<testPokemon?> = _pokemonDetail

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchPokemonDetail(nameOrId: String) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val detail = pokemonApiService.getPokemonDetail(nameOrId)
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch Pokemon details: ${e.message}"
                _pokemonDetail.value = null
            }
        }
    }
}



