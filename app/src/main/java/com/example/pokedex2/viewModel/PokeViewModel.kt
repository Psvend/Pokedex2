package com.example.pokedex2.viewModel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.PokemonPagingSource
import com.example.pokedex2.data.remote.PokemonResult
import com.example.pokedex2.data.remote.json.testPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
   private val pokemonApiService: PokemonApiService


) : ViewModel() {
    val pokemonPagingFlow: Flow<PagingData<PokemonResult>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PokemonPagingSource(pokemonApiService) }
    ).flow.cachedIn(viewModelScope)

    private val _pokemonDetail = MutableStateFlow<testPokemon?>(null)
    val pokemonDetail: StateFlow<testPokemon?> = _pokemonDetail




    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            val result = pokemonApiService.getPokemonDetail(name)
            _pokemonDetail.value = result
    }
}


}





