package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.model.Affirmation
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
    //private val mainPageViewModel: MainPageViewModel
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Affirmation>>(emptyList())
    val pokemonList: StateFlow<List<Affirmation>> = _pokemonList

    /*
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
     */

    fun syncPokemons(apiPokemons: List<Affirmation>) {
        viewModelScope.launch {
            val likedPokemons = favouritesRepository.getFavourites().firstOrNull() ?: emptyList()
            val syncedPokemons = apiPokemons.map { fetched ->
                fetched.copy(isLiked = likedPokemons.any { it.id == fetched.id })
            }

            _pokemonList.value = syncedPokemons
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
/*
    fun getIsLikedById(id: Int): Boolean {
        return _pokemonList.value.find { it.id == id }?.isLiked ?: false
    }

    fun getIsLikedByName(name: String): Boolean {
        return _pokemonList.value.find { it.name == name }?.isLiked ?: false
    }

    fun getAffirmationByName(name: String): Affirmation? {
        return _pokemonList.value.find { it.name == name}
    }*/

    fun getAffirmationById(id: Int): Affirmation? {
        return _pokemonList.value.find { it.id == id}
    }

    /*fun toggleLikeById(pokemonId: Int) {
        viewModelScope.launch {
            // Find the Pokémon in the list by id
            val affirmation = _pokemonList.value.find { it.id == pokemonId }
            affirmation?.let {
                val isLiked = !it.isLiked
                val updatedAffirmation = it.copy(isLiked = isLiked)

                // Update cache (favourites repository)
                if (isLiked) {
                    favouritesRepository.addFavourite(updatedAffirmation)
                } else {
                    favouritesRepository.removeFavourite(updatedAffirmation.id)
                }

                // Update the list with the new isLiked state
                _pokemonList.update { list ->
                    list.map { if (it.id == pokemonId) updatedAffirmation else it }
                }
            }
        }
    }*/

}
