package com.example.pokedex2.data

import com.example.pokedex2.R
import com.example.pokedex2.model.affirmation

class DatasourcePokemon() {
    fun loadAffirmations(): List<affirmation> {
        return listOf<affirmation>(
            affirmation("Bulbasaur", R.drawable.bulbasaur_background),
            affirmation("Ivysaur", R.drawable.ivysaur_background),
            affirmation("Venusaur", R.drawable.venusaur_background),
            affirmation("Charmander", R.drawable.charmander_background),
            affirmation("Charmeleon", R.drawable.charmeleon_background),
            affirmation("Charizard", R.drawable.charizard_background),
            affirmation("Squirtle", R.drawable.squirtle_background),
            affirmation("Wartortle", R.drawable.wartortle_background),
            affirmation("Blastoise", R.drawable.blastoise_background),
            affirmation("Caterpie", R.drawable.caterpie_background)
        )
    }
}


