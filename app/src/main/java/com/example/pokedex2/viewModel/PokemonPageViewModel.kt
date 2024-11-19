package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val description: String
)

class PokemonPageViewModel : ViewModel() {
    private val _pokemonId = MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId

    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon: StateFlow<Pokemon?> = _pokemon

    fun setPokemonId(id: String) {
        viewModelScope.launch {
            _pokemonId.emit(id)
            fetchPokemonData(id)
        }
    }

    private fun fetchPokemonData(id: String) {
        // Simulate fetching data from a repository or API
        val fetchedPokemon = Pokemon(
            id = id.toInt(),
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            types = listOf("Grass", "Poison"),
            description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun’s rays, the seed grows progressively larger."
        )
        _pokemon.value = fetchedPokemon
    }
}