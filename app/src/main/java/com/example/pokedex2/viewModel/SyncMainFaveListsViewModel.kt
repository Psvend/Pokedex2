package com.example.pokedex2.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository,
    private val pokemonApiService: PokemonApiService
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Affirmation>>(emptyList())
    val pokemonList: StateFlow<List<Affirmation>> = _pokemonList

    val isLoading = mutableStateOf(false)
    val isPaginating = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    private var currentPage = 0

    init {
        syncData()
    }

    fun syncData(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) isLoading.value = true else isPaginating.value = true

                val likedPokemons = favouritesRepository.getFavourites().firstOrNull() ?: emptyList()
                val fetchedPokemons = fetchPokemonsFromApi(page)

                val syncedPokemons = fetchedPokemons.map { fetched ->
                    fetched.copy(isLiked = likedPokemons.any { it.id == fetched.id })
                }

                _pokemonList.update { if (page == 0) syncedPokemons else it + syncedPokemons }
                currentPage = page
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Oops! Something went wrong. Please try again."
            } finally {
                if (page == 0) isLoading.value = false else isPaginating.value = false
            }
        }
    }

    private suspend fun fetchPokemonsFromApi(page: Int): List<Affirmation> {
        // Replace this with paginated API logic
        val response = pokemonApiService.getPokemonList(0, 20)
        return response.results.map { result ->
            val detail = pokemonApiService.getPokemonDetail(result.name)
            Affirmation(
                id = detail.id,
                name = detail.name.capitalizeFirstLetter(),
                imageResourceId = detail.sprites.front_default ?: "",
                typeIcon = detail.types.map { it.type.name.capitalizeFirstLetter() },
                isLiked = false, // Will be updated during sync
                number = detail.id
            )
        }
    }

    fun toggleLike(affirmation: Affirmation) {
        viewModelScope.launch {
            val isLiked = !affirmation.isLiked
            val updatedAffirmation = affirmation.copy(isLiked = isLiked)

            // Update cache
            if (isLiked) {
                favouritesRepository.addFavourite(updatedAffirmation)
            } else {
                favouritesRepository.removeFavourite(updatedAffirmation.id)
            }

            // Update list
            _pokemonList.update { list ->
                list.map { if (it.id == affirmation.id) updatedAffirmation else it }
            }
        }
    }
    fun loadNextPage() {
        syncData(currentPage + 1)
    }
}

