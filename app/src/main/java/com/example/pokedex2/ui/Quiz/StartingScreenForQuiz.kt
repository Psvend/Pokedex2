package com.example.pokedex2.ui.Quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokedex2.R

@Composable
fun StartingScreenForQuiz(
    modifier: Modifier = Modifier,
    navController: NavHostController


) {
    Column (
        modifier = modifier.fillMaxSize()
        .background(Color(0xFFFFF9E6))
        .padding(WindowInsets.safeDrawing.asPaddingValues()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


            Image(
                painter = painterResource(id = R.drawable.blankpokemon),
                contentDescription = "pokemon",
                modifier = Modifier.size(250.dp),
            )

        Button(
            onClick = { navController.navigate("Quiz") },
            modifier = Modifier
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(Color(0xFF1DB5D4)),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp
            )
        ) {
            Text("Start Quiz")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.bug_image),
            contentDescription = "pokemon",
            modifier = Modifier.size(180.dp),
            contentScale = ContentScale.Crop
        )
    }
}