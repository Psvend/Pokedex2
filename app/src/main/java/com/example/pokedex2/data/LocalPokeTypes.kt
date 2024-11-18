package com.example.pokedex2.data


data class LocalPokeTypes(
    val id: Int,
    val name: String,
    val color: String
)

class DataPokeTypes {
    fun loadTypes(): List<LocalPokeTypes> {
        return listOf(
            LocalPokeTypes(1,"Bug", "#A8B820"),
            LocalPokeTypes(2,"Dark", "#705848"),
            LocalPokeTypes(3,"Dragon", "#7038F8"),
            LocalPokeTypes(4,"Electric", "#F8D030"),
            LocalPokeTypes(5, "Fairy", "#EE99AC"),
            LocalPokeTypes(6,"Fighting", "#C03028"),
            LocalPokeTypes(7,"Fire", "#F08030"),
            LocalPokeTypes(8,"Flying", "#A890F0"),
            LocalPokeTypes(9,"Ghost", "#705898"),
            LocalPokeTypes(10,"Grass", "#78C850"),
            LocalPokeTypes(11,"Ground", "#E0C068"),
            LocalPokeTypes(12,"Ice", "#98D8D8"),
            LocalPokeTypes(13,"Normal", "#A8A878"),
            LocalPokeTypes(14,"Poison", "#A040A0"),
            LocalPokeTypes(15,"Psychic", "#F85888"),
            LocalPokeTypes(16,"Rock", "#B8A038"),
            LocalPokeTypes(17,"Steel", "#B8B8D0"),
            LocalPokeTypes(18,"Water", "#6890F0")
        )
    }
}




