package com.example.pokedex2.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.LocalCaching
import com.example.pokedex2.data.local.LocalCachingDao
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.Filters.addSpaceAndCapitalize
import com.example.pokedex2.ui.Filters.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor (
    private val pokemonApiService: PokemonApiService,
    private val localCachingDao: LocalCachingDao,

) : ViewModel() {

    private val _apiPokemons = MutableStateFlow<List<Affirmation>>(emptyList())
    val apiPokemons: StateFlow<List<Affirmation>> = _apiPokemons

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _headPokemonAffirmation = MutableStateFlow<Affirmation?>(null)
    val headPokemonAffirmation: StateFlow<Affirmation?> = _headPokemonAffirmation

    private var currentPage = 0


    init {
        loadPokemonsFromDatabase()
    }

    fun getPokemonAffirmation(name: String): Affirmation? {
        _headPokemonAffirmation.value = _apiPokemons.value.find{it.name == name }
        return _headPokemonAffirmation.value
    }

    private fun fetchAffirmations(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) _isLoading.value = true else _isPaginating.value = true

                val offset = 0
                val response = pokemonApiService.getPokemonList(offset, 1025)

                val fetchedPokemons = response.results.map { result ->
                    val detail = pokemonApiService.getPokemonDetail(result.name)

                    Affirmation(
                        id = detail.id,
                        name = detail.name.capitalizeFirstLetter().addSpaceAndCapitalize(),
                        imageResourceId = detail.sprites.front_default,
                        typeIcon = detail.types.map { it.type.name.capitalizeFirstLetter().addSpaceAndCapitalize() },
                        isLiked = false,
                        number = detail.id,
                        ability = detail.abilities.map { it.ability.name },
                        heldItem = detail.held_items.map { it.item.name },
                        stats = detail.stats.map { it.stat.name.capitalizeFirstLetter().addSpaceAndCapitalize() to it.base_stat }
                    )
                }

                // Update database for local Caching
                localCachingDao.insertPokemons(fetchedPokemons.map {
                    LocalCaching(
                        id = it.id,
                        name = it.name,
                        imageResourceId = it.imageResourceId,
                        typeIcon = it.typeIcon.joinToString(","),
                        isLiked = it.isLiked,
                        number = it.number,
                        ability = it.ability.joinToString(","),
                        heldItem = it.heldItem.joinToString(","),
                        stats = it.stats.joinToString(",") { stat -> "${stat.first}:${stat.second}" }
                    )
                })

                _apiPokemons.update { if (page == 0) fetchedPokemons else it + fetchedPokemons }
                currentPage = page
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Oops! Something went wrong. Please try again."
            } finally {
                if (page == 0) _isLoading.value = false else _isPaginating.value = false
            }
        }
    }

    private fun loadPokemonsFromDatabase() {
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
            // Show cached data immediately
            _apiPokemons.value = cachedPokemons
            if (cachedPokemons.size < 1024) {
                fetchAffirmations()
            }
        }

    }





    fun loadNextPage() {
        fetchAffirmations(currentPage + 1)
    }

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

    private var _booleanDefault = MutableStateFlow<Boolean>(false)
    val booleanDefault : StateFlow<Boolean> = _booleanDefault

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

    fun toggleLike(affirmation: Affirmation) {
        viewModelScope.launch {
            _pokemonLike.value = localCachingDao.getPokemonByname(affirmation.name)
            val isLiked = !_pokemonLike.value!!.isLiked
            val pokemon = _pokemonLike.value!!.copy(isLiked = isLiked)
            // Update cache
            if (isLiked) {
                localCachingDao.addLike(pokemon.name)
                _booleanDefault.value = true
                affirmation.isLiked = true

            } else {
                localCachingDao.removeLike(pokemon.name)
                _booleanDefault.value = true
                affirmation.isLiked = false
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
