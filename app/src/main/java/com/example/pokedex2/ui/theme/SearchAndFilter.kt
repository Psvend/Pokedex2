package com.example.pokedex2.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import kotlinx.serialization.Serializable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val category: String,
    val type: List<String>,
    val abilities: List<String>,
    val stats: Stats
)

@Serializable
data class Stats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int
)

@Composable
fun loadPokemonItems(): MutableList<String> {
    var items = remember { mutableStateListOf<String>() }

    // Load JSON file and parse it
    val jsonString = File("src/main/res/pokeSample.json").readText()
    val pokemonList = Json.decodeFromString<List<Pokemon>>(jsonString)

    // Populate items with the names of the Pokémon
    items.addAll(pokemonList.map { it.name })

    return items
}

@Preview (showBackground = true)
@Composable
fun TempView() {

    val items = loadPokemonItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        for (item in items) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Red)
            ) {

            }
        }
    }
}
