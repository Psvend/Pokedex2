package com.example.pokedex2.data

import com.example.pokedex2.model.LocalGenerations

class DataPokeGenerations{
    fun loadGeneration(): List<LocalGenerations> {
        return listOf(
            LocalGenerations(19,"I", 1..151),
            LocalGenerations(20,"II", 152..251),
            LocalGenerations(21,"III", 252..386),
            LocalGenerations(22,"IV",387..493),
            LocalGenerations(23,"V",494..649),
            LocalGenerations(24,"VI", 650..721),
            LocalGenerations(25,"VII",722..809),
            LocalGenerations(26,"VIII",810..905),
            LocalGenerations(27, "IX",906..1025)
        )
    }
}
