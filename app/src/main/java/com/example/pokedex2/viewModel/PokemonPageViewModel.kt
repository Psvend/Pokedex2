package com.example.pokedex2.viewModel

/*
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


data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val description: String
)
@HiltViewModel
class PokemonPageViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {
    private val _pokemonId = MutableStateFlow<String?>(null)
    val pokemonId: StateFlow<String?> = _pokemonId

    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon: StateFlow<Pokemon?> = _pokemon
    private val _affirmations = MutableStateFlow<List<Affirmation>>(emptyList())
    val affirmations: StateFlow<List<Affirmation>> = _affirmations
    private val _pokemonDetail = MutableStateFlow<testPokemon?>(null)
    val pokemonDetail: StateFlow<testPokemon?> = _pokemonDetail



    fun setPokemonId(id: String, pokeViewModel: PokeViewModel) {
        viewModelScope.launch {
            _pokemonId.emit(id)
            pokeViewModel.fetchPokemonDetail(id)
        }
    }

    fun observePokemonDetail(pokeViewModel: PokeViewModel) {
        viewModelScope.launch {
            pokeViewModel.pokemonDetail.collect { detail ->
                detail?.let {
                    _pokemon.value = Pokemon(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.sprites.front_default, // Assuming front_default is the image URL
                        types = it.types.map { type -> type.type.name },
                        description = "Description not available" //
                    )
                }
            }
        }
    }
    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                val detail = pokemonApiService.getPokemonDetail(name)
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}

 */