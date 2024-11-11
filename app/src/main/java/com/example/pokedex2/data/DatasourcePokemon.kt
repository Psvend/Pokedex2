package com.example.pokedex2.data

import com.example.pokedex2.R
import com.example.pokedex2.model.affirmation
import kotlin.reflect.typeOf

class DatasourcePokemon() {
    fun loadAffirmations(): List<affirmation> {
        return listOf<affirmation>(
            affirmation(
                R.string.Bulbasaur,
                R.drawable.bulbasaur_background,
                listOf("Grass", "Poison"),
                "# 1"
                ),
            affirmation
                (R.string.Ivysaur,
                R.drawable.ivysaur_background,
                listOf("Grass", "Poison"),
                "# 2"
            ),
            affirmation(
                R.string.Venusaur,
                R.drawable.venusaur_background,
                listOf("Grass", "Poison"),
                "# 3"
            ),
            affirmation(
                R.string.Charmander,
                R.drawable.charmander_background,
                listOf("Fire"),
                "# 4"
            ),
            affirmation(
                R.string.Charmeleon,
                R.drawable.charmeleon_background,
                listOf("Fire"),
                "# 5"
            ),
            affirmation(
                R.string.Charizard,
                R.drawable.charizard_background,
                listOf("Fire", "Flying"),
                "# 6"
            ),
            affirmation(
                R.string.Squirtle,
                R.drawable.squirtle_background,
                listOf("Water"),
                "# 7"
            ),
            affirmation(
                R.string.Wartortle,
                R.drawable.wartortle_background,
                listOf("Water"),
                "# 8"
            ),
            affirmation(
                R.string.Blastoise,
                R.drawable.blastoise_background,
                listOf("Water"),
                "# 9"
            ),
            affirmation(
                R.string.Caterpie,
                R.drawable.caterpie_background,
                listOf("Bug"),
                "# 10"
            )
            )
    }
}


