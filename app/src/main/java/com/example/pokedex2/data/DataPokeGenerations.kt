package com.example.pokedex2.data

import com.example.pokedex2.model.LocalGenerations

class DataPokeGenerations{
    fun loadGeneration(): List<LocalGenerations> {
        return listOf(
            LocalGenerations(1,"I"),
            LocalGenerations(2,"II"),
            LocalGenerations(3,"III"),
            LocalGenerations(4,"IV"),
            LocalGenerations(5,"V"),
            LocalGenerations(6,"VI"),
            LocalGenerations(7,"VII"),
            LocalGenerations(8,"VIII")
        )
    }
}
