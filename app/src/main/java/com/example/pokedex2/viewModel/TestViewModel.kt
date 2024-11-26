package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonTest(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val description: String,
    val isLiked: Boolean = false
)


@HiltViewModel
class TestViewModel @Inject constructor(
): ViewModel()
    {
    private val _pokemonTest = MutableStateFlow<PokemonTest?>(null)
    val pokemonTest: MutableStateFlow<PokemonTest?> = _pokemonTest

    fun fetchPokemonDetail(pokeViewModel: PokeViewModel) {
        viewModelScope.launch {
            pokeViewModel.pokemonDetail.collect{ detail ->

                detail?.let {
                    _pokemonTest.value = PokemonTest(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.sprites.front_default,
                        types = it.types.map { type -> type.type.name },
                        description = "Description not available",
                        isLiked = it.isLiked
                    )
                }
            }
        }
    }
}
