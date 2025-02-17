package com.example.pokedex2.viewModel

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.R
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.json.PokemonResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService

) :ViewModel() {

    private val _pokemonDetail = MutableStateFlow<PokemonResult?>(null)
    val pokemonDetail: StateFlow<PokemonResult?> = _pokemonDetail

    private val _pokemonNames = MutableStateFlow<List<String>>(emptyList())
    val pokemonNames: StateFlow<List<String>> = _pokemonNames

    init {
        fetchPokemonNames()
    }

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            val result = pokemonApiService.getPokemonDetail(name)
            _pokemonDetail.value = result
        }
    }
    private fun fetchPokemonNames() {
        viewModelScope.launch {
            val response = pokemonApiService.getPokemonList(0, 1025) // Fetch first 100 Pokémon names
            val names = response.results.map { it.name }
            _pokemonNames.value = names
        }
    }

    fun getRandomPokemonId(): Int {
        return Random.nextInt(1, 1025)
    }

fun playSound(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.whos_that_pokemon)
        mediaPlayer.start()
    }

}