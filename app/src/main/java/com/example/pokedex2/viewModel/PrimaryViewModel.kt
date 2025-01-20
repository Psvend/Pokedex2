package com.example.pokedex2.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.data.local.LocalCaching
import com.example.pokedex2.data.local.LocalCachingDao
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimaryViewModel @Inject constructor(
    private val localCachingDao: LocalCachingDao, // Inject LocalCachingDao to access cached data
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<LocalCaching?>(null)
    val pokemonDetail: StateFlow<LocalCaching?> = _pokemonDetail

    private val _pokemonDetailList = MutableStateFlow<List<LocalCaching?>>(emptyList())
    val pokemonDetailList: StateFlow<List<LocalCaching?>> = _pokemonDetailList

    private val _pokemonLikedList = MutableStateFlow<List<LocalCaching?>>(emptyList())
    val pokemonLikedList: StateFlow<List<LocalCaching?>> = _pokemonLikedList

    private val _convertedDetail = MutableStateFlow<List<Affirmation?>>(emptyList())
    val convertedDetail: StateFlow<List<Affirmation?>> = _convertedDetail

    fun getAllLikedPokemons() : List<LocalCaching?> {
        viewModelScope.launch {
            Log.d("tag 1", "${localCachingDao.getAllFavourites()}")

            _pokemonLikedList.value = localCachingDao.getAllFavourites()
        }
        return _pokemonLikedList.value
    }

    fun getAllPokemon(): List<LocalCaching?> {
        viewModelScope.launch {
            _pokemonDetailList.value = localCachingDao.getAllPokemons()
        }
        return _pokemonDetailList.value
    }

    fun fetchCachedPokemon(pokemonIdOrName: String) {
        viewModelScope.launch {
            val pokemon = localCachingDao.getAllPokemons()
                .find { it.name.equals(pokemonIdOrName, ignoreCase = true) }
            _pokemonDetail.value = pokemon
        }
    }

    fun convertToAffirmation(listLocalCaching: List<LocalCaching?>): List<Affirmation?> {
        viewModelScope.launch {
            val cachedPokemons = listLocalCaching.map {
                it?.let { it1 ->
                    Affirmation(
                        id = it1.id,
                        name = it.name,
                        imageResourceId = it.imageResourceId,
                        typeIcon = it.typeIcon.split(","),
                        isLiked = it.isLiked,
                        number = it.number,
                        ability = it.ability.split(","),
                        heldItem = it.heldItem.split(","),
                        stats = it.stats.split(",").map { stat ->
                            val parts = stat.split(":")
                            parts[0] to parts[1].toInt()
                        }
                    )
                }
            }
            _convertedDetail.value = cachedPokemons
        }
        return _convertedDetail.value
    }

    fun toggleLike(name: String) {
        viewModelScope.launch {
            val pokemon = localCachingDao.getPokemonByname(name)
            val newIsLiked = !pokemon.isLiked

            // Update the database
            if (newIsLiked) {
                localCachingDao.addLike(name)
            } else {
                localCachingDao.removeLike(name)
            }

            // Update the _pokemonLikedList to reflect the new state
            _pokemonLikedList.update { currentList ->
                currentList.map { pokemonItem ->
                    if (pokemonItem?.name == name) pokemonItem.copy(isLiked = newIsLiked)
                    else pokemonItem
                }
            }
        }
    }






}