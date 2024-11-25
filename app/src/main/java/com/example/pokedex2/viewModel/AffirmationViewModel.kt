package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.pokedex2.viewModel.AffirmationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AffirmationViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : ViewModel() {
    private val _affirmations = MutableStateFlow<List<Affirmation>>(emptyList())
    val affirmations: StateFlow<List<Affirmation>> = _affirmations

    init {
        fetchAffirmations()
    }

    private fun fetchAffirmations() {
        viewModelScope.launch {
            try {
                val response = pokemonApiService.getPokemonList(0, 20)
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
                _affirmations.value = affirmationsList
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    fun toggleLike(affirmation: Affirmation) {
        _affirmations.update { list ->
            list.map {
                if (it == affirmation) it.copy(isLiked = !it.isLiked) else it
            }
        }
    }
}