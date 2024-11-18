package com.example.pokedex2.data

import androidx.compose.ui.res.stringResource
import com.example.pokedex2.R
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.viewModel.AffirmationViewModel


object DatasourcePokemon {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(
                R.string.Bulbasaur,
                R.drawable.bulbasaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 1",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
                ),
            Affirmation
                (R.string.Ivysaur,
                R.drawable.ivysaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 2",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Venusaur,
                R.drawable.venusaur_background,
                listOf(R.drawable.grass_background, R.drawable.poison_background),
                "# 3",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Charmander,
                R.drawable.charmander_background,
                listOf(R.drawable.fire_background),
                "# 4",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Charmeleon,
                R.drawable.charmeleon_background,
                listOf(R.drawable.fire_background),
                "# 5" ,
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Charizard,
                R.drawable.charizard_background,
                listOf(R.drawable.fire_background, R.drawable.flying_background),
                "# 6",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Squirtle,
                R.drawable.squirtle_background,
                listOf(R.drawable.water_background),
                "# 7",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Wartortle,
                R.drawable.wartortle_background,
                listOf(R.drawable.water_background),
                "# 8",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Blastoise,
                R.drawable.blastoise_background,
                listOf(R.drawable.water_background),
                "# 9",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            ),
            Affirmation(
                R.string.Caterpie,
                R.drawable.caterpie_background,
                listOf(R.drawable.bugtype_background),
                "# 10",
                description = "The is the cutest of them all",
                graph = R.drawable.graph_test
            )
            )
    }
}


