package com.example.pokedex2.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex2.data.remote.json.PokemonResult
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource (
    private val pokemonApiService: PokemonApiService
) : PagingSource<Int,PokemonResult>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val position = params.key ?: 0
        return try {
            val response = pokemonApiService.getPokemonList(offset = position, limit = params.loadSize)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (response.next == null) null else position + params.loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }

}