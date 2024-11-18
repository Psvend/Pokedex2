package com.example.pokedex2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokedex2.model.Affirmation

class FavoritesViewModel {
    private val _favorites = MutableLiveData<List<Affirmation>>()

    fun addToFavorites(affirmation: Affirmation) {
        val currentList = _favorites.value.orEmpty().toMutableList()
        currentList.add(affirmation)
        _favorites.value = currentList
    }
    fun removeFromFavorites(affirmation: Affirmation) {
        val currentList = _favorites.value.orEmpty().toMutableList()
        currentList.remove(affirmation)
        _favorites.value = currentList
    }
    fun getFavorites(): LiveData<List<Affirmation>> {
        val favoriteListCopy = _favorites
        return favoriteListCopy
    }
}
/* parent fun for updating both favorites and pokemon page
val favoritesViewModel: FavoritesViewModel = viewModel()
val affirmationViewModel: AffirmationViewModel = viewModel(factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AffirmationViewModel(favoritesViewModel) as T
    }
})

 */


