package com.example.pokedex2.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import kotlinx.serialization.Serializable
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.serialization.json.Json

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

fun loadPokemonItems(context: Context): List<Pokemon> {
    return try {
        val jsonString = context.assets.open("pokeSample.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<Pokemon>>(jsonString)
    } catch (e: Exception) {
        println("Error loading pokemon items: ${e.message}")
        emptyList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TempView() {
    var active by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    var pokemonList by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    //val pokemonList = remember { loadPokemonItems(context = LocalContext.current) }
    LaunchedEffect(Unit) {
        pokemonList = loadPokemonItems(
            context
        )
    }
    println(context)
    Scaffold {
        SearchBar(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(color = Color.Red),
            query = "",
            onQueryChange = { text = it },
            onSearch = { },
            active = active,
            onActiveChange = {active = it},
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search-icon within the search bar") },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close-icon within the search bar"
                    )
                }
            }

        ) {
            pokemonList.forEach {
                Row(
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Face, contentDescription = "Pokemon Icon")
                    Text(text = it.name) // Display Pokemon name
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun DefaultPreview() {
    TempView()
}

