package com.example.pokedex2.viewModel

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pokedex2.data.DataPokeTypes
import androidx.compose.ui.graphics.Color


class SearchViewModel : ViewModel() {
    val pokeTypes = DataPokeTypes().loadTypes().sortedBy { it.name }
    val selectionMap = mutableStateMapOf<Int, Boolean>()
    var searchQuery = mutableStateOf("Name, number or description")
    var active = mutableStateOf(false)
    var showDialog = mutableStateOf(false)
    var acceptSearchCriteria = mutableStateOf(false)

    fun getTypeColor(typeId: Int, defaultColor: String): Color {
        return if (selectionMap[typeId] == true) {
            Color(android.graphics.Color.parseColor(defaultColor)) // Selected color
        } else {
            Color.Black // Default unselected color
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
        pokeTypes.forEach { selectionMap[it.id] = true }
    }

    fun validateCriteria() {
        if (selectionMap.containsValue(true) || searchQuery.value.isNotBlank() && searchQuery.value != "Name, number or description") {
            acceptSearchCriteria.value = true
        } else {
            showDialog.value = true
        }
    }
}
