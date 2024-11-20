package com.example.pokedex2.data.mappers

import com.example.pokedex2.data.local.PokemonEntity
import com.example.pokedex2.data.remote.PokemonDto
import com.example.pokedex2.data.remote.PokemonResult
import com.example.pokedex2.model.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        types = types.map { it.type.name }
    )
}
fun PokemonResult.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = 0, // Assuming id is not available in PokemonResult
        name = name,
        height = 0, // Assuming height is not available in PokemonResult
        weight = 0, // Assuming weight is not available in PokemonResult
        types = listOf()
    )
}