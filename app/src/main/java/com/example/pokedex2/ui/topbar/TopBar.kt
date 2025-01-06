package com.example.pokedex2.ui.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokedex2.ui.menuNav.NavGraph
import com.example.pokedex2.R
import com.example.pokedex2.viewModel.TopBarViewModel

@Composable
fun TopBar(
    viewModel: TopBarViewModel = viewModel(),
    navController: NavHostController
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showFilterOverlay by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .height(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .background(Color(0xFFE55655).copy(alpha = 0.9f))
                    .height(24.dp)
            ) {}
            Image(
                painter = painterResource(id = R.drawable.top_bar_background),
                contentDescription = "Top Bar Background",
                alpha = 0.9f,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
            Button(
                onClick = { /* Handle button click */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 75.dp, end = 16.dp)
                    .size(width = 45.dp, height = 11.dp)
            ) {
                Text("Button")
            }
            Button(
                onClick = { navController.navigate("mainPage") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 36.dp, start = 16.dp)
                    .size(width = 70.dp, height = 70.dp)
            ) {
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}