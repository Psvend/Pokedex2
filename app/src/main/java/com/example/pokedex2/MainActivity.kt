package com.example.pokedex2

import com.example.pokedex2.model.Pokemon
import com.example.pokedex2.data.Datasource
import androidx.compose.material3.Card
import androidx.compose.foundation.Image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex2.ui.theme.Pokedex2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pokedex2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    PokemonCard(modifier = Modifier, pokemon = Pokemon)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hi $name!",
        modifier = modifier
    )
}

//Attempt to make scrollable list of pokemons
@Composable
fun PokemonList(pokemonList: List<Pokemon>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier) {
        items(pokemonList) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, modifier: Modifier) {
    Card(modifier = modifier) {
        Column {
            Image (
                painter = painterResource(pokemon.imageResourceId),
                contentDescription = stringResource(pokemon.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
                )
            Text(
                text = LocalContext.current.getString(pokemon.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Pokedex2Theme {
        Greeting("Android")
        PokemonCard(Pokemon(R.string.pokemon1,R.drawable.image1))
    }
}