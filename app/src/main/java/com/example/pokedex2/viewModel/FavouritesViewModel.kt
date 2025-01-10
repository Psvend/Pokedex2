package com.example.pokedex2.viewModel
/*
import androidx.constraintlayout.helper.widget.Flow
import androidx.lifecycle.ViewModel
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    fun getFavouriteAffirmations(): Flow<List<Affirmation>> {
        return favouritesRepository.getFavourites()
    }

    suspend fun addFavourite(affirmation: Affirmation) {
        favouritesRepository.addFavourite(affirmation)
    }

    suspend fun removeFavourite(affirmationId: Int) {
        favouritesRepository.removeFavourite(affirmationId)
    }
}


/*

class FavouritesViewModel @Inject constructor() : ViewModel() {

    private val _favourites = MutableStateFlow<List<Affirmation>>(emptyList())
    val favourites: StateFlow<List<Affirmation>> = _favourites

    fun getFavouriteAffirmations(context: Context) {
        viewModelScope.launch {
            val cachedFavourites = listOf(getFavouriteAffirmations(context))
            _favourites.value = cachedFavourites
        }
    }

    fun toggleLike(context: Context, affirmation: Affirmation) {
        viewModelScope.launch {
            if (affirmation.isLiked) {
                removeFavouriteAffirmation(context, affirmation.id)
            } else {
                saveFavouritePokemon(context, affirmation)
            }
            // Update the local state
            getFavouriteAffirmations(context)
        }
    }
}
*/