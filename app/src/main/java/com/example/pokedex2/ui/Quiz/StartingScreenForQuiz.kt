package com.example.pokedex2.ui.Quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun StartingScreenForQuiz(
    modifier: Modifier = Modifier,
    navController: NavHostController


) {
    Column (   modifier = modifier.fillMaxSize()
        .background(Color(0xFFFFF9E6))
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)){

        Text(
            text = "Who´s that pokemon?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { navController.navigate("Quiz") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Start Quiz")
        }


    }
}

@Preview
@Composable
fun StartingScreenForQuizPreview() {
    //StartingScreenForQuiz()
}