package com.example.pokedex2.viewModel

import android.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.pokedex2.viewModel.AffirmationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AffirmationViewModel @Inject() constructor(
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

                    //fetching encounter locations
                    val encounters = pokemonApiService.getPokemonEncounters(detail.location_area_encounters)
                    val locationNames = encounters.map { it.location_area.name.capitalizeFirstLetter()}



                    Affirmation(
                        id = detail.id,
                        name = detail.name.capitalizeFirstLetter(),   //changed first letter to upper case
                        imageResourceId = detail.sprites.front_default ?: "",
                        typeIcon = detail.types.map { it.type.name.capitalizeFirstLetter() },
                        isLiked = false,
                        number = detail.id,
                        encounterLocations = locationNames
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

    fun toggleLike(affirmation: Affirmation) {
        _affirmations.update { list ->
            list.map {
                if (it == affirmation) it.copy(isLiked = !it.isLiked) else it
            }
        }
    }



}