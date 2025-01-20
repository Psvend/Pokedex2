package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.LocalCaching
import com.example.pokedex2.data.local.LocalCachingDao
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokePageViewModel @Inject constructor (
    private val localCachingDao: LocalCachingDao // Inject LocalCachingDao to access cached data
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<LocalCaching?>(null)
    val pokemonDetail: StateFlow<LocalCaching?> = _pokemonDetail

    private val _convertedDetail = MutableStateFlow<List<Affirmation>>(emptyList())
    val convertedDetail: StateFlow<List<Affirmation>> = _convertedDetail


    fun fetchCachedPokemon(pokemonIdOrName: String) {
        viewModelScope.launch {
            val pokemon = localCachingDao.getAllPokemons()
                .find { it.name.equals(pokemonIdOrName, ignoreCase = true) }
            _pokemonDetail.value = pokemon
        }
    }

    fun convertToAffirmation(localCaching: LocalCaching?): List<Affirmation>{
        viewModelScope.launch {
            // Load cached pokemons first
            val cachedPokemons = localCachingDao.getAllPokemons().map {
                Affirmation(
                    id = it.id,
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
            _convertedDetail.value = cachedPokemons
        }
        return _convertedDetail.value
    }
    // This can still be used for fetching API data if required
    fun getAffirmationByName(pokemonIdOrName: String) : Affirmation? {
        return _convertedDetail.value.find { it.name.equals(pokemonIdOrName, ignoreCase = true) }
    }
}