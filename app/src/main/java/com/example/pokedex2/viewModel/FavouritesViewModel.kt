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

    fun getFavouriteAffirmations(): Flow<List<Affirmation>> {
        return getFavouriteAffirmations(context)
    }

    suspend fun saveFavourite(affirmation: Affirmation) {
        saveFavouritePokemon(context, affirmation)
    }

    suspend fun removeFavourite(affirmationId: Int) {
        removeFavouriteAffirmation(context, affirmationId)
    }
}


