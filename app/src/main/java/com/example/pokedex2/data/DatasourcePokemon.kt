package com.example.pokedex2.data

import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation

class DatasourcePokemon() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(
                R.string.Bulbasaur,
                R.drawable.bulbasaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 1"
                ),
            Affirmation
                (R.string.Ivysaur,
                R.drawable.ivysaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 2"
            ),
            Affirmation(
                R.string.Venusaur,
                R.drawable.venusaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 3"
            ),
            Affirmation(
                R.string.Charmander,
                R.drawable.charmander_background,
                listOf(R.drawable.fire_background),
                "# 4"
            ),
            Affirmation(
                R.string.Charmeleon,
                R.drawable.charmeleon_background,
                listOf(R.drawable.fire_background),
                "# 5"
            ),
            Affirmation(
                R.string.Charizard,
                R.drawable.charizard_background,
                listOf(R.drawable.fire_background, R.drawable.flying_background),
                "# 6"
            ),
            Affirmation(
                R.string.Squirtle,
                R.drawable.squirtle_background,
                listOf(R.drawable.water_background),
                "# 7"
            ),
            Affirmation(
                R.string.Wartortle,
                R.drawable.wartortle_background,
                listOf(R.drawable.water_background),
                "# 8"
            ),
            Affirmation(
                R.string.Blastoise,
                R.drawable.blastoise_background,
                listOf(R.drawable.water_background),
                "# 9"
            ),
            Affirmation(
                R.string.Caterpie,
                R.drawable.caterpie_background,
                listOf(R.drawable.bugtype_background),
                "# 10"
            )
            )
    }
}


