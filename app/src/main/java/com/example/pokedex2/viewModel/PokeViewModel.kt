package com.example.pokedex2.viewModel

import network.RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex2.data.remote.PokemonDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PokeViewModel : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonDto>>(emptyList())
    val pokemonList: StateFlow<List<PokemonDto>> = _pokemonList
    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            RetrofitInstance.api.getPokemonList().enqueue(object : Callback<List<PokemonDto>> {
                override fun onResponse(call: Call<List<PokemonDto>>, response: Response<List<PokemonDto>>) {
                    if (response.isSuccessful) {
                        _pokemonList.value = (response.body() ?: emptyList()) as List<PokemonDto>
                    }
                }

                override fun onFailure(call: Call<List<PokemonDto>>, t: Throwable) {
                    // Handle error
                }
            })
        }
    }
}
