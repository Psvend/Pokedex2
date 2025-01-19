package com.example.pokedex2.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllPokemonsViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    private val _affirmations = MutableStateFlow<List<Affirmation>>(emptyList())
    val affirmations: StateFlow<List<Affirmation>> = _affirmations

    val isLoading = mutableStateOf(true)
    val isPaginating = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun fetchAffirmations(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) isLoading.value = true else isPaginating.value = true

                val offset = page * 20
                val response = pokemonApiService.getPokemonList(offset, 20)

                val affirmationsList = response.results.map { result ->
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

                _affirmations.update { it + affirmationsList }
            } catch (e: Exception) {
                errorMessage.value = "Oops! Something went wrong. Please try again."
            } finally {
                isLoading.value = false
                isPaginating.value = false
            }
        }
    }
}
