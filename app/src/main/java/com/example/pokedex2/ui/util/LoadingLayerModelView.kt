package com.example.pokedex2.ui.util

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RotatingLoaderViewModel : ViewModel() {

    // add a state isLoading
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading
    private val _rotation = mutableStateOf(0f)
    val rotation: State<Float> get() = _rotation

    init {
        startRotation()
    }

    private fun startRotation(rotationDuration: Int = 2000) {
        viewModelScope.launch {
            while (true) {
                val rotationStep = 360f / (rotationDuration / 16f)
                for (i in 1..(rotationDuration / 16)) {
                    _rotation.value = (_rotation.value + rotationStep) % 360f
                    delay(16)
                }
            }
        }
    }
}