package com.example.pokedex2.ui.Quiz
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
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
    val selectedAnswer = remember { mutableStateOf<String?>(null) }
    val triggerNextQuistion = remember { mutableStateOf(false) }
    val isClear = remember { mutableStateOf(false) }
    val answerCounts = remember { mutableIntStateOf(0) }
    val maxQuestions=10
    val time = remember { mutableIntStateOf(0) }
    val isTimeOut = remember { mutableStateOf(true) }

    LaunchedEffect(isTimeOut.value) {
        if (isTimeOut.value) {
         while (isTimeOut.value) {
             kotlinx.coroutines.delay(1000)
             time.value += 1

         }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetail(randomPokemonId.toString())
    }

    LaunchedEffect (triggerNextQuistion.value) {
        if (triggerNextQuistion.value && answerCounts.value < maxQuestions) {
            isClear.value = true
            kotlinx.coroutines.delay(1000)
            isClear.value = false
            selectedAnswer.value = null
            triggerNextQuistion.value = false
            val randomPokemonId = viewModel.getRandomPokemonId()
            viewModel.fetchPokemonDetail(randomPokemonId.toString())
            answerCounts.value += 1
            if (answerCounts.value == maxQuestions) {
                isTimeOut.value = false
            }
        }
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
        .padding(8.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Who´s that pokemon?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        if (answerCounts.value < maxQuestions) {

            QuizImagae(model = pokemonDetail.value?.sprites?.front_default, isClear = isClear.value)

            Text(
                text = "Score: ${points.value}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                answerOptions.forEach { option ->
                    Button(
                        onClick = {
                            selectedAnswer.value = option
                            if (option == pokemonDetail.value?.name) {
                                points.value += 1
                            }
                            triggerNextQuistion.value = true

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                selectedAnswer.value == option && option == pokemonDetail.value?.name -> Color.Green
                                selectedAnswer.value == option -> Color.Red
                                else -> Color.Blue
                            },
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = option
                        )
                    }
                }
            }

        }else {
            Text(
                text = "Game Over",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Your score is: ${points.value}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
            if(points.intValue==0){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/122.png",
                        contentDescription = "You are a pokemon master",
                        modifier = Modifier
                            .size(240.dp, 240.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
            else if (time.intValue<30){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/025.png",
                        contentDescription = "You are a pokemon master",
                        modifier = Modifier
                            .size(240.dp, 240.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }else if(time.intValue in 31..59){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                AsyncImage(
                    model = "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png",
                    contentDescription = "You are a pokemon master",
                    modifier = Modifier
                        .size(240.dp, 240.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                }
            }else {
                AsyncImage(
                    model = "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/143.png",
                    contentDescription = "You are a pokemon master",
                    modifier = Modifier
                        .size(240.dp, 240.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
                Text(
                text = "Time: ${time.value} seconds",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun QuizImagae(model : String?, isClear: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
                    colorFilter = if(isClear)null else ColorFilter.tint(Color.Black),
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