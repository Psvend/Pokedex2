package com.example.pokedex2.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavViewModel {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


}