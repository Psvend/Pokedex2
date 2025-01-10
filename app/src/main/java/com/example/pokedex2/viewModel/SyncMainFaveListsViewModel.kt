package com.example.pokedex2.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val mainPageViewModel: MainPageViewModel,
    private val favouritesViewModel: FavouritesViewModel<Any?>
) : ViewModel() {

    fun toggleLike(context: Context, affirmation: Affirmation) {
        val isLiked = !affirmation.isLiked
        val updatedAffirmation = affirmation.copy(isLiked = isLiked)

        viewModelScope.launch {
            if (isLiked) {
                favouritesViewModel.addFavourite(updatedAffirmation)
            } else {
                favouritesViewModel.removeFavourite(updatedAffirmation.id)
            }

            mainPageViewModel.updateAffirmation(updatedAffirmation)
        }
    }
}

