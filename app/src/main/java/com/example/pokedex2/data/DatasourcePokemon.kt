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
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 1"
                ),
            affirmation
                (R.string.Ivysaur,
                R.drawable.ivysaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 2"
            ),
            affirmation(
                R.string.Venusaur,
                R.drawable.venusaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 3"
            ),
            affirmation(
                R.string.Charmander,
                R.drawable.charmander_background,
                listOf(R.drawable.fire_background),
                "# 4"
            ),
            affirmation(
                R.string.Charmeleon,
                R.drawable.charmeleon_background,
                listOf(R.drawable.fire_background),
                "# 5"
            ),
            affirmation(
                R.string.Charizard,
                R.drawable.charizard_background,
                listOf(R.drawable.fire_background, R.drawable.flying_background),
                "# 6"
            ),
            affirmation(
                R.string.Squirtle,
                R.drawable.squirtle_background,
                listOf(R.drawable.water_background),
                "# 7"
            ),
            affirmation(
                R.string.Wartortle,
                R.drawable.wartortle_background,
                listOf(R.drawable.water_background),
                "# 8"
            ),
            affirmation(
                R.string.Blastoise,
                R.drawable.blastoise_background,
                listOf(R.drawable.water_background),
                "# 9"
            ),
            affirmation(
                R.string.Caterpie,
                R.drawable.caterpie_background,
                listOf(R.drawable.bugtype_background),
                "# 10"
            )
            )
    }
}


