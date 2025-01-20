package com.example.pokedex2.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.local.LocalCaching
import com.example.pokedex2.data.local.LocalCachingDao
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor (
    private val pokemonApiService: PokemonApiService,
    private val localCachingDao: LocalCachingDao // Inject the DAO

) : ViewModel() {

    private val _apiPokemons = MutableStateFlow<List<Affirmation>>(emptyList())
    val apiPokemons: StateFlow<List<Affirmation>> = _apiPokemons

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var currentPage = 0


    init {
        loadPokemonsFromDatabase()
    }


    private fun fetchAffirmations(page: Int = 0) {
        viewModelScope.launch {
            try {
                if (page == 0) _isLoading.value = true else _isPaginating.value = true

                val offset = page * 20
                val response = pokemonApiService.getPokemonList(offset, 20)

                val fetchedPokemons = response.results.map { result ->
                    val detail = pokemonApiService.getPokemonDetail(result.name)

                    Affirmation(
                        id = detail.id,
                        name = detail.name.capitalizeFirstLetter(),
                        imageResourceId = detail.sprites.front_default ?: "",
                        typeIcon = detail.types.map { it.type.name.capitalizeFirstLetter() },
                        isLiked = false,
                        number = detail.id,
                        ability = detail.abilities.map { it.ability.name },
                        heldItem = detail.held_items.map { it.item.name },
                        stats = detail.stats.map { it.stat.name.capitalizeFirstLetter() to it.base_stat }
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

            // Load new data from the API
            fetchAffirmations()
        }
    }


    fun getPokemonFromDatabaseByName(name: String): Affirmation? {
        return _apiPokemons.value.find { it.name.equals(name, ignoreCase = true) }

    }


        fun loadNextPage() {
        fetchAffirmations(currentPage + 1)
    }

    fun getAffirmationByName(name: String): Affirmation? {
        return _apiPokemons.value.find { it.name == name}
    }
}
