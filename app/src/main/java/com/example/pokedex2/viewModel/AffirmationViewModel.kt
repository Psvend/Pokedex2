package com.example.pokedex2.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.Pokemon
import com.example.pokedex2.data.local.removeFavouritePokemon
import com.example.pokedex2.data.local.saveFavouritePokemon
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AffirmationViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {
    private val _affirmations = MutableStateFlow<List<Affirmation>>(emptyList())
    val affirmations: StateFlow<List<Affirmation>> = _affirmations

    val isLoading = mutableStateOf(true)
    val isPaginating = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    private var currentPage = 0

    init {
        fetchAffirmations()
    }

    // Make fetchAffirmations public
    fun fetchAffirmations(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) isLoading.value = true else isPaginating.value = true

                val offset = page * 20
                val response = pokemonApiService.getPokemonList(offset, 20)

                val affirmationsList = response.results.map { result ->
                    val detail = pokemonApiService.getPokemonDetail(result.name)
                    Affirmation(
                        id = detail.id,
                        name = detail.name,
                        imageResourceId = detail.sprites.front_default ?: "",
                        typeIcon = detail.types.map { it.type.name },
                        isLiked = false,
                        number = detail.id
                    )
                }

                _affirmations.update { it + affirmationsList }
                currentPage = page
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Oops! Something went wrong. Please try again."
            } finally {
                isLoading.value = false
                isPaginating.value = false
            }
        }
    }

    // Add a helper function to calculate the next page
    fun loadNextPage() {
        fetchAffirmations(currentPage + 1)
    }

    fun toggleLike(context: Context, affirmation: Affirmation) {
        Log.d("ToggleLike", "Before: ${affirmation.isLiked}")
        _affirmations.update { list ->
            list.map {
                if (it == affirmation) {
                    val updatedAffirmation = it.copy(isLiked = !it.isLiked)
                    updateCache(context, updatedAffirmation) // Persist to DataStore
                    updatedAffirmation

                } else it
            }
        }
    }

    private fun updateCache(context: Context, affirmation: Affirmation) {
        viewModelScope.launch {
            val pokemon = Pokemon.newBuilder()
                .setId(affirmation.id)
                .setName(affirmation.name)
                .setImageUrl(affirmation.imageResourceId)
                .addAllTypes(affirmation.typeIcon)
                .build()

            if (affirmation.isLiked) {
                saveFavouritePokemon(context, pokemon)
            } else {
                removeFavouritePokemon(context, pokemon.id)
            }
        }
    }
}
