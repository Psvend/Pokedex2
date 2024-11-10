package com.example.pokedex2.ui.theme

import android.annotation.SuppressLint
import android.provider.ContactsContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.R
import kotlinx.serialization.*
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
fun loadPokemonItems(): List<Pokemon> {
    val path = "app/src/main/res/pokeSample.json"
    val items = Json.decodeFromString<ContactsContract.Contacts.Data>(File(path).readText())
    return item
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TempView() {
    var active by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    // Load Pokémon names from JSON file
    val items = remember {
        loadPokemonItems()
    }

    //Wrap scaffold in box for red background

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
            items.forEach {pokemon ->
                Row(
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Face, contentDescription = "Pokemon Icon")
                    Text(text = pokemon.name) // Display Pokemon name
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