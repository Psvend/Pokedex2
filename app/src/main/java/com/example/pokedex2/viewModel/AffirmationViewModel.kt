package com.example.pokedex2.viewModel

import com.example.pokedex2.data.DatasourcePokemon
import androidx.lifecycle.ViewModel
import com.example.pokedex2.model.Affirmation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.pokedex2.viewModel.AffirmationViewModel

class AffirmationViewModel(private val favoritesViewModel: FavoritesViewModel) : ViewModel() {
    private val _affirmations = MutableStateFlow<List<Affirmation>>(emptyList())
    val affirmations: StateFlow<List<Affirmation>> = _affirmations

    init {
        // Load data into affirmations
        _affirmations.value = com.example.pokedex2.data.DatasourcePokemon.loadAffirmations()
    }

    fun toggleLike(affirmation: Affirmation) {
        _affirmations.update { list ->
            list.map {
                if (it == affirmation) {
                    val updatedAffirmation = it.copy(isLiked = !it.isLiked)

                    // Update favorites list
                    if (updatedAffirmation.isLiked) {
                        favoritesViewModel.addToFavorites(updatedAffirmation)
                    } else {
                        favoritesViewModel.removeFromFavorites(updatedAffirmation)
                    }

                    updatedAffirmation
                } else it
            }
        }
    }
}