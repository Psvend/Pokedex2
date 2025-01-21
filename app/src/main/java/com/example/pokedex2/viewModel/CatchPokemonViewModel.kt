package com.example.pokedex2.viewModel

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.R
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchPokemonViewModel @Inject constructor(
    private val pokemonApiService: PokemonApiService,
) : ViewModel() {

    private val _currentStep = MutableStateFlow(AnimationStep.Idle)
    val currentStep: StateFlow<AnimationStep> = _currentStep

    private val _currentPokemon = MutableStateFlow<Pokemon?>(null)
    val currentPokemon: StateFlow<Pokemon?> get() = _currentPokemon

    fun fetchRandomPokemon() {
        viewModelScope.launch {
            val randomId = (1..1015).random()
            try {
                val pokemon = pokemonApiService.getPokemonDetail(randomId.toString())
                _currentPokemon.value = Pokemon(
                    name = pokemon.name,
                    type = pokemon.types.map { it.type.name },
                    image = pokemon.sprites.front_default,
                    number = pokemon.id,
                    sprites = pokemon.sprites
                )
                startAnimation()
            } catch (e: Exception) {
                Log.e("Catch", "Error fetching Pokémon", e)
            }
        }
    }

    fun startAnimation() {
        _currentStep.value = AnimationStep.CatchAnimation
    }

    fun proceedToNextStep() {
        when (_currentStep.value) {
            AnimationStep.CatchAnimation -> _currentStep.value = AnimationStep.SparkleAnimation
            AnimationStep.SparkleAnimation -> _currentStep.value = AnimationStep.ShowDialog
            else -> _currentStep.value = AnimationStep.Idle
        }
    }

    fun reset() {
        _currentStep.value = AnimationStep.Idle
        _currentPokemon.value = null
    }

    fun playSound(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.whos_that_pokemon)
        mediaPlayer.start()
    }

    enum class AnimationStep {
        Idle,
        CatchAnimation,
        SparkleAnimation,
        ShowDialog
    }
}
