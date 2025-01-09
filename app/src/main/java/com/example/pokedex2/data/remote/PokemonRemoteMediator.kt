package com.example.pokedex2.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.pokedex2.data.local.PokemonDatabase
import com.example.pokedex2.data.local.PokemonEntity
import com.example.pokedex2.data.mappers.toPokemonEntity

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator (
    private val database: PokemonDatabase,
    private val pokemonApi: PokemonApiService
): RemoteMediator<Int,PokemonEntity>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0
                    } else {
                        lastItem.id
                    }
                }
            }
            val response = pokemonApi.getPokemonList(offset = loadKey, limit = state.config.pageSize)
            Log.d("API_CALL", "Response: $response")
            val pokemonEntities = response.results.map { it.toPokemonEntity() }
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.pokemonDao().clearAll()
                }
                database.pokemonDao().upsert(pokemonEntities)
            }
            MediatorResult.Success(endOfPaginationReached = response.next == null)
        }catch (e: Exception){
            MediatorResult.Error(e)
        }catch (e: HttpException){
            MediatorResult.Error(e)
        }
    }
}