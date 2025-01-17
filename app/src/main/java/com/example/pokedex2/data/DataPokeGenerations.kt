package com.example.pokedex2.data

import com.example.pokedex2.model.LocalGenerations

class DataPokeGenerations{
    fun loadGeneration(): List<LocalGenerations> {
        return listOf(
            LocalGenerations(19,"I"),
            LocalGenerations(20,"II"),
            LocalGenerations(21,"III"),
            LocalGenerations(22,"IV"),
            LocalGenerations(23,"V"),
            LocalGenerations(24,"VI"),
            LocalGenerations(25,"VII"),
            LocalGenerations(26,"VIII")
        )
    }
}
