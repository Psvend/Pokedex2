package com.example.pokedex2.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex2.data.remote.PokemonApiService
import com.example.pokedex2.data.remote.PokemonPagingSource
import com.example.pokedex2.data.remote.PokemonResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject() constructor(
   private val pokemonApiService: PokemonApiService


) : ViewModel() {
    val pokemonPagingFlow: Flow<PagingData<PokemonResult>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PokemonPagingSource(pokemonApiService) }
    ).flow.cachedIn(viewModelScope)

    }

