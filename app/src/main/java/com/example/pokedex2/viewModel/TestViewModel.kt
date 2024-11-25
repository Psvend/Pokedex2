package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Pokemontest(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val description: String
)


@HiltViewModel
class TestViewModel @Inject constructor(
    private val PokemonApiService: PokemonApiService
): ViewModel()
    {
    private val _pokemontest = MutableStateFlow<Pokemontest?>(null)
    val pokemontest: MutableStateFlow<Pokemontest?> = _pokemontest

        fun setPokemonId(id: String, pokeViewModel: PokeViewModel) {
            viewModelScope.launch {
                _pokemontest.emit(pokemontest.value)
                pokeViewModel.fetchPokemonDetail(id)
            }
        }
    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            val result = PokemonApiService.getPokemonDetail(name)
            _pokemontest.value = result
        }
    }

    fun ofetchPokemonDetail(pokeViewModel: PokeViewModel) {
        viewModelScope.launch {
            pokeViewModel.pokemonDetail.collect{ detail ->

                detail?.let {
                    _pokemontest.value = Pokemontest(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.sprites.front_default,
                        types = it.types.map { type -> type.type.name },
                        description = "Description not available"
                    )
                }
            }


        }
    }

}
