package com.example.pokedex2.data

import com.example.pokedex2.R
import com.example.pokedex2.model.Pokemon

class Datasource() {
    fun loadPokemon(): List<Pokemon> {
        return listOf<Pokemon>(
            Pokemon(R.string.pokemon1, R.drawable.image1),
            Pokemon(R.string.pokemon2, R.drawable.image2),
            Pokemon(R.string.pokemon3, R.drawable.image3),
            Pokemon(R.string.pokemon4, R.drawable.image4),
            Pokemon(R.string.pokemon5, R.drawable.image5),
            Pokemon(R.string.pokemon6, R.drawable.image6),
    }
}