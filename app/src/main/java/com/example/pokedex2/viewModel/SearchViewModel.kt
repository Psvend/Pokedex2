package com.example.pokedex2.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex2.data.DataPokeTypes
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.data.DataPokeEvolutions
import com.example.pokedex2.data.DataPokeGenerations
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.model.LocalEvolution
import com.example.pokedex2.model.LocalGenerations
import com.example.pokedex2.model.LocalPokeTypes


class SearchViewModel : ViewModel() {
    val pokeTypes = mutableStateOf<List<LocalPokeTypes>>(emptyList())
    val pokeGenerations = mutableStateOf<List<LocalGenerations>>(emptyList())
    val pokeEvolutions = mutableStateOf<List<LocalEvolution>>(emptyList())
    val isLoading = mutableStateOf(true)
    val selectionMap = mutableStateMapOf<Int, Boolean>()
    val selectionGenMap = mutableStateMapOf<Int, Boolean>()
    val selectionEvoMap = mutableStateMapOf<Int, Boolean>()
    var pokeList = mutableStateOf<List<Affirmation>>(listOf())
    private var cachedPokemonSearch = listOf<Affirmation>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)
    

    init {
        Log.d("SearchViewModel", "Initializing ViewModel...")
        loadTypes()
        loadGenerations()
        loadEvolutions()
    }

    private fun loadTypes() {
        Log.d("SearchViewModel", "Loading types...")
        val types = DataPokeTypes().loadTypes()
        pokeTypes.value = types

        // Log types
        Log.d("SearchViewModel", "Types loaded: $types")

        types.forEach { type ->
            selectionMap[type.id] = true
        }

        // Log selectionMap. Printing in logcat
        Log.d("SearchViewModel", "SelectionMap initialized: $selectionMap")

        isLoading.value = false
    }

    private fun loadGenerations() {
        Log.d("SearchViewModel", "Loading generations...")
        val generations = DataPokeGenerations().loadGeneration()
        pokeGenerations.value = generations

        //Log generations
        Log.d("SearchViewModel", "Generations loaded: $generations")

        generations.forEach {generation->
            selectionGenMap[generation.id] = true
        }

        isLoading.value = false
    }

    private fun loadEvolutions() {
        Log.d("SearchViewModel", "Loading evolutions...")
        val evolutions = DataPokeEvolutions().loadEvolutions()
        pokeEvolutions.value = evolutions

        //Log generations
        Log.d("SearchViewModel", "Evolutions loaded: $evolutions")

        evolutions.forEach {evolution->
            selectionEvoMap[evolution.id] = true
        }
        isLoading.value = false
    }

    fun getTypeColor(typeId: Int, defaultColor: String): Color {
        return if (selectionMap[typeId] == true) {
            Color(android.graphics.Color.parseColor(defaultColor)) // Selected color
        } else {
            Color.DarkGray
        }
    }

    fun getButtonColor(id: Int): Color {
        return if (selectionGenMap[id] == true ||selectionEvoMap[id] == true) {
            Color(0xFFA91E1E)
        } else {
            Color.DarkGray
        }
    }

    fun toggleSelection(id: Int) {
        if (id <= 18) {
            selectionMap[id] = !(selectionMap[id] ?: false)
        } else if (id in 19..26){
            selectionGenMap[id] = !(selectionGenMap[id] ?: false)
        } else if (id in 27..29){
            selectionEvoMap[id] = !(selectionEvoMap[id] ?: false)
        }
    }
}
