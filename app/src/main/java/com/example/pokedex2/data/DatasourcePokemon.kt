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
                listOf(R.drawable.category_type_grass),
                "#0001"
                ),
            affirmation
                (R.string.Ivysaur,
                R.drawable.ivysaur_background,
                listOf(R.drawable.category_type_grass),
                "#0002"
            ),
            affirmation(
                R.string.Venusaur,
                R.drawable.venusaur_background,
                listOf(R.drawable.category_type_grass),
                "#0003"
            ),
            affirmation(
                R.string.Charmander,
                R.drawable.charmander_background,
                listOf(R.drawable.category_type_fire),
                "#0004"
            ),
            affirmation(
                R.string.Charmeleon,
                R.drawable.charmeleon_background,
                listOf(R.drawable.category_type_fire),
                "#0005"
            ),
            affirmation(
                R.string.Charizard,
                R.drawable.charizard_background,
                listOf(R.drawable.category_type_fire),
                "#0006"
            ),
            affirmation(
                R.string.Squirtle,
                R.drawable.squirtle_background,
                listOf(R.drawable.category_type_water),
                "#0007"
            ),
            affirmation(
                R.string.Wartortle,
                R.drawable.wartortle_background,
                listOf(R.drawable.category_type_water),
                "#0008"
            ),
            affirmation(
                R.string.Blastoise,
                R.drawable.blastoise_background,
                listOf(R.drawable.category_type_water),
                "#0009"
            ),
            affirmation(
                R.string.Caterpie,
                R.drawable.caterpie_background,
                listOf(R.drawable.category_type_bug),
                "#0010"
            )
            )
    }
}


