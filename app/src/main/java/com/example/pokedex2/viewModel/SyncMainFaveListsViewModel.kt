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
    private val mainPageViewModel: MainPageViewModel
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Affirmation>>(emptyList())
    val pokemonList: StateFlow<List<Affirmation>> = _pokemonList

    val isLoading: StateFlow<Boolean> = mainPageViewModel.isLoading
    val isPaginating: StateFlow<Boolean> = mainPageViewModel.isPaginating
    val errorMessage: StateFlow<String?> = mainPageViewModel.errorMessage

    init {
        viewModelScope.launch {
            mainPageViewModel.apiPokemons.collect { apiPokemons ->
                val likedPokemons = favouritesRepository.getFavourites().firstOrNull() ?: emptyList()

                val syncedPokemons = apiPokemons.map { fetched ->
                    fetched.copy(isLiked = likedPokemons.any { it.id == fetched.id })
                }

                _pokemonList.value = syncedPokemons
            }
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

            // Update synced list
            _pokemonList.update { list ->
                list.map { if (it.id == affirmation.id) updatedAffirmation else it }
            }
        }
    }

    fun loadNextPage() {
        mainPageViewModel.loadNextPage()
    }
}
