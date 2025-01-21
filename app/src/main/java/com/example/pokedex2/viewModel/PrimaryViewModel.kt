package com.example.pokedex2.viewModel

/*
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

    private val _getPokemon = MutableStateFlow<LocalCaching?>(null)
    val getPokemon: StateFlow<LocalCaching?> = _getPokemon

    private val _pokemonList = MutableStateFlow<List<LocalCaching>>(emptyList())
    val pokemonList: StateFlow<List<LocalCaching>> = _pokemonList

    private val _pokemonLike = MutableStateFlow<LocalCaching?>(null)
    val pokemonLike: StateFlow<LocalCaching?> = _pokemonLike

    private val _pokemonPlaceholder = MutableStateFlow<LocalCaching?>(null)
    val pokemonPlaceholder: StateFlow<LocalCaching?> = _pokemonPlaceholder

    private val _convertedDetail = MutableStateFlow<List<Affirmation?>>(emptyList())
    val convertedDetail: StateFlow<List<Affirmation?>> = _convertedDetail

    private val _convertedAffirmation = MutableStateFlow<Affirmation?>(null)
    val convertedAffirmation: StateFlow<Affirmation?> = _convertedAffirmation

    fun getPokemonByName(name: String) : LocalCaching? {
        viewModelScope.launch {
            val pokemon = localCachingDao.getPokemonByname(name)
            _getPokemon.value=pokemon
        }
        return _getPokemon.value
    }

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

            _pokemonLikedList.update { list ->
                list.map {
                    if (it?.id == pokemon.id) pokemon else it}
            }
        }
    }
    /*
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


        }

    }

_pokemonList.update { currentList ->
                currentList.map { pokemonItem ->
                    if (pokemonItem.name == name) pokemonItem.copy(isLiked = isLiked)
                    else pokemonItem
                }
            }


     */




}

 */