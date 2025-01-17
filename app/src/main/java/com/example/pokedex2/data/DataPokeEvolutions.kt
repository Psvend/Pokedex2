package com.example.pokedex2.data

import com.example.pokedex2.model.LocalEvolution

class DataPokeEvolutions {
    fun loadEvolutions(): List<LocalEvolution> {
        return listOf(
            LocalEvolution(27, "I"),
            LocalEvolution(28, "II"),
            LocalEvolution(29, "III")
        )
    }
}