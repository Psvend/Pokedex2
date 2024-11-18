package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonPageViewModel : ViewModel() {
    private val _pokemonId =MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId
    fun setPokemonId(id: String) {
        viewModelScope.launch {
         _pokemonId.emit(id)
        }
    }
}