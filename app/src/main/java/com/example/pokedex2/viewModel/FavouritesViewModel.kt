package com.example.pokedex2.viewModel

import android.content.Context
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.ViewModel
import com.example.pokedex2.data.local.getFavouriteAffirmations
import com.example.pokedex2.data.local.removeFavouriteAffirmation
import com.example.pokedex2.data.local.saveFavouritePokemon
import com.example.pokedex2.model.Affirmation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    // Retrieve the flow of favorite affirmations
    fun getFavouriteAffirmations(): Flow<List<Affirmation>> {
        return getFavouriteAffirmations(context)
    }

    // Save a favorite Pokémon
    suspend fun saveFavourite(affirmation: Affirmation) {
        saveFavouritePokemon(context, affirmation)
    }

    // Remove a favorite Pokémon
    suspend fun removeFavourite(affirmationId: Int) {
        removeFavouriteAffirmation(context, affirmationId)
    }
}

