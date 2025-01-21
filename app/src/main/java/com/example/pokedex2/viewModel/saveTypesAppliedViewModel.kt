package com.example.pokedex2.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class saveTypesAppliedViewModel(private val state: SavedStateHandle): ViewModel() {
    private val _strings: SnapshotStateList<String> = state.get("strings") ?: mutableStateListOf()

    var strings: SnapshotStateList<String>
        get() = _strings
        set(value) {
            _strings.clear()
            _strings.addAll(value)
            state["strings"] = _strings
        }

    fun addString(newString: String) {
        if (!_strings.contains(newString)) {
            _strings.add(newString)
            state["strings"] = _strings
        }
    }

    fun removeString(oldString: String) {
        if (_strings.contains(oldString)) {
            _strings.remove(oldString)
            state["strings"] = _strings
        }
    }
}