package com.example.pokedex2.ui.Quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex2.viewModel.QuizViewModel


@Composable
fun Quiz(
    viewModel: QuizViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val pokemonDetail = viewModel.pokemonDetail.collectAsState()
    val pokemonNames = viewModel.pokemonNames.collectAsState()
    val randomPokemonId = remember { viewModel.getRandomPokemonId() }
    val points = remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetail(randomPokemonId.toString())
    }
    val answerOptions = remember(pokemonDetail.value, pokemonNames.value) {
        if (pokemonDetail.value != null) {
            val correctAnswer = pokemonDetail.value?.name ?: "Unknown"
            (pokemonNames.value.shuffled().take(3) + correctAnswer).shuffled()
        } else {
            emptyList()
        }
    }
    Column (modifier = modifier.fillMaxSize()
        .background(Color(0xFFFFF9E6))
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                QuizImagae(model = pokemonDetail.value?.sprites?.front_default)
            }

        Text(
            text = "Who´s that pokemon?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text =  points.value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            answerOptions.forEach { option ->
                Button(
                    onClick = {
                        if (option == pokemonDetail.value?.name) {
                            points.value += 1
                            val newRandomPokemonId = viewModel.getRandomPokemonId()
                            viewModel.fetchPokemonDetail(newRandomPokemonId.toString())

                        } else {
                            val newRandomPokemonId = viewModel.getRandomPokemonId()
                            viewModel.fetchPokemonDetail(newRandomPokemonId.toString())
                            /* Handle wrong answer */
                        }
                     },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = option)
                }
            }
        }
    }

}
@Composable
fun QuizImagae(
    model : String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = "{pokemonDetail?.name} sprite",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .size(240.dp, 240.dp)
                        .clip(RoundedCornerShape(12.dp))

                )
            } else {
                Text(
                    text = "Image not available",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


    @Preview
    @Composable
    fun QuizPreview() {
val viewModel: QuizViewModel = hiltViewModel()

        Quiz(viewModel)
    }