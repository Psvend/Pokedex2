package com.example.pokedex2.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex2.data.DataPokeTypes
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.data.DataPokeGenerations
import com.example.pokedex2.model.LocalGenerations
import com.example.pokedex2.model.LocalPokeTypes


class SearchViewModel : ViewModel() {
    val pokeTypes = mutableStateOf<List<LocalPokeTypes>>(emptyList())
    val pokeGenerations = mutableStateOf<List<LocalGenerations>>(emptyList())
    val isLoading = mutableStateOf(true)
    val selectionMap = mutableStateMapOf<Int, Boolean>()
    val selectionGenerationMap = mutableStateMapOf<String, Boolean>()
    var searchQuery = mutableStateOf("Name, number or description")
    var active = mutableStateOf(false)
    var showDialog = mutableStateOf(false)
    var acceptSearchCriteria = mutableStateOf(false)

    init {
        Log.d("SearchViewModel", "Initializing ViewModel...")
        loadTypes()
    }

    private fun loadTypes() {
        Log.d("SearchViewModel", "Loading types...")
        val types = DataPokeTypes().loadTypes()
        pokeTypes.value = types

        // Log types
        Log.d("SearchViewModel", "Types loaded: $types")

        types.forEach { type ->
            selectionMap[type.id] = false
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

        generations.forEach {generations->
            selectionGenerationMap[generations.generation] = false
        }

        isLoading.value = false
    }

    fun getTypeColor(typeId: Int, defaultColor: String): Color {
        return if (selectionMap[typeId] == true) {
            Color(android.graphics.Color.parseColor(defaultColor)) // Selected color
        } else {
            Color.DarkGray // Unselected type color
        }
    }

    fun clearSearch() {
        searchQuery.value = ""
        active.value = false
        acceptSearchCriteria.value = false
        selectionMap.clear()
    }

    fun toggleSelection(id: Int) {
        selectionMap[id] = !(selectionMap[id] ?: false)
    }

    fun selectAll() {
        pokeTypes.value.forEach { selectionMap[it.id] = true }
    }

    fun validateCriteria() {
        if (selectionMap.containsValue(true) || searchQuery.value.isNotBlank() && searchQuery.value != "Name, number or description") {
            acceptSearchCriteria.value = true
        } else {
            showDialog.value = true
        }
    }
}
