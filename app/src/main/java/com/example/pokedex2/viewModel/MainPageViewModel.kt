package com.example.pokedex2.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor (
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    private val _apiPokemons = MutableStateFlow<List<Affirmation>>(emptyList())
    val apiPokemons: StateFlow<List<Affirmation>> = _apiPokemons

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var currentPage = 0

    init {
        fetchAffirmations() // Fetch API data on ViewModel initialization
    }

    private fun fetchAffirmations(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) _isLoading.value = true else _isPaginating.value = true

                val offset = page * 20
                val response = pokemonApiService.getPokemonList(offset, 20)

                val fetchedPokemons = response.results.map { result ->
                    val detail = pokemonApiService.getPokemonDetail(result.name)
                    val characterDetails = pokemonApiService.getCharacteristic(result.id)
                    val speciesDetails = pokemonApiService.getPokemonSpecies(result.name)

                    val evolutionChainUrl = speciesDetails.evolution_chain.url
                    val evolutionChainId = evolutionChainUrl.split("/").last { it.isNotEmpty() }.toInt()

                    Affirmation(
                        id = detail.id,
                        name = detail.name.capitalizeFirstLetter(),
                        imageResourceId = detail.sprites.front_default ?: "",
                        typeIcon = detail.types.map { it.type.name.capitalizeFirstLetter() },
                        isLiked = false,
                        number = detail.id,
                        ability = detail.abilities.map { it.ability.name },
                        heldItem = detail.held_items.map { it.item.name },
                        characteristics = characterDetails.descriptions.map { it.description },
                        growthRate = speciesDetails.growth_rate.name,
                        evolutionChainId = evolutionChainId,
                        stats = detail.stats.map { it.stat.name.capitalizeFirstLetter() to it.base_stat }
                    )
                }

                _apiPokemons.update { if (page == 0) fetchedPokemons else it + fetchedPokemons }
                currentPage = page
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Oops! Something went wrong. Please try again."
            } finally {
                if (page == 0) _isLoading.value = false else _isPaginating.value = false
            }
        }
    }

    fun loadNextPage() {
        fetchAffirmations(currentPage + 1)
    }

    fun getAffirmationByName(name: String): Affirmation? {
        return _apiPokemons.value.find { it.name == name}
    }
}
