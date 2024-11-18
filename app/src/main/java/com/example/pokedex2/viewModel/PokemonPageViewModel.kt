package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.pokedex2.data.DatasourcePokemon
import kotlinx.coroutines.launch
import com.example.pokedex2.model.Affirmation

class PokemonPageViewModel : ViewModel() {

    //State for currently selected pokemon Id
    private val _pokemonId =MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId

    //State for evolutions
    private val _evolutions = MutableStateFlow<List<Affirmation>>(emptyList())
    val evolutions: StateFlow<List<Affirmation>> = _evolutions

    //State for current evolution index
    private val _currentEvolution = MutableStateFlow(0)
    val currentEvolution: StateFlow<Int> = _currentEvolution

    //Set the pokemon Id and load its evolutions
    fun setPokemonId(id: String) {
        viewModelScope.launch {
         _pokemonId.emit(id)
         loadEvolutions(id)
        }
    }

    //Update the current evolution index when user swipes
    fun onEvolutionChanged(newIndex: Int) {
        _currentEvolution.value = newIndex
    }

    //Load evolutions based on the pokemon ID
    private fun loadEvolutions(pokemonId: String) {
        viewModelScope.launch {
            //Get all pokemons from the list
            val allPokemon = DatasourcePokemon.loadAffirmations()

            //Find the pokemon by its ID
            val selectedPokemon = allPokemon.find { it.number == pokemonId }

            if (selectedPokemon != null ) {
                //Filter pokemon by if they are evolution 1, 2 or 3 (evolution number)
                val evolutionaryChain = allPokemon.filter {
                    it.evolutionNumber == selectedPokemon.evolutionNumber
                }
                _evolutions.emit(evolutionaryChain)
            }
        }
    }
}