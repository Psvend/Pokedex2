package com.example.pokedex2.data.mappers

import com.example.pokedex2.data.local.PokemonEntity
import com.example.pokedex2.data.remote.PokemonDto
import com.example.pokedex2.model.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        number = number,
        type = type,
        image = image
    )
}
fun PokemonEntity.tpPokemon(): Pokemon {
    return Pokemon(
        name = name,
        number = number,
        type = type,
        image = image
    )
}