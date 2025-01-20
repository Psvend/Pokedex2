package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.FavouritesRepository
import com.example.pokedex2.data.local.LocalCaching
import com.example.pokedex2.data.local.LocalCachingDao
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
    private val localCachingDao: LocalCachingDao,
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<LocalCaching>>(emptyList())
    val pokemonList: StateFlow<List<LocalCaching>> = _pokemonList

    private val _pokemonLike = MutableStateFlow<LocalCaching?>(null)
    val pokemonLike: StateFlow<LocalCaching?> = _pokemonLike

    private val _convertedAffirmation = MutableStateFlow<Affirmation?>(null)
    val convertedAffirmation: StateFlow<Affirmation?> = _convertedAffirmation

    fun syncPokemons(pokemonList: List<LocalCaching>) {
        viewModelScope.launch {
            val likedPokemons = favouritesRepository.getFavourites().firstOrNull() ?: emptyList()
            val syncedPokemons = pokemonList.map { fetched ->
                fetched.copy(isLiked = likedPokemons.any { it.id == fetched.id })
            }

            _pokemonList.value = syncedPokemons
        }
    }

    fun convertSingleAffirmation(localCaching : LocalCaching) : Affirmation? {
        viewModelScope.launch {
            val pokemon =
                Affirmation(
                    id = localCaching.id,
                    name = localCaching.name,
                    imageResourceId = localCaching.imageResourceId,
                    typeIcon = localCaching.typeIcon.split(","),
                    isLiked = localCaching.isLiked,
                    number = localCaching.number,
                    ability = localCaching.ability.split(","),
                    heldItem = localCaching.heldItem.split(","),
                    stats = localCaching.stats.split(",").map { stat ->
                        val parts = stat.split(":")
                        parts[0] to parts[1].toInt()
                    }
                )
            _convertedAffirmation.value = pokemon

        }
        return _convertedAffirmation.value
    }

    fun toggleLike(name: String) {
        viewModelScope.launch {
            _pokemonLike.value = localCachingDao.getPokemonByname(name)
            val isLiked = !_pokemonLike.value!!.isLiked
            val pokemon = _pokemonLike.value!!.copy(isLiked = isLiked)

            // Update cache
            if (isLiked) {
                localCachingDao.addLike(pokemon.name)

            } else {
                    localCachingDao.removeLike(pokemon.name)
                }

            _pokemonList.update { list ->
                list.map { if (it.id == pokemon.id) pokemon else it }
            }
        }

    }

}
