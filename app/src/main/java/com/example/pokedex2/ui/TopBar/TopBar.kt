package com.example.pokedex2.ui.TopBar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.NewTopBar
import com.example.pokedex2.R
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.viewModel.TopBarViewModel

/*
@Composable
fun TopBar(
    viewModel: TopBarViewModel = viewModel(),
    navController: NavHostController
) {
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
            )
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
                onClick = {
                    if(navController.currentDestination?.route == "pokemonPage/{pokemonName}") {
                        navController.navigate("mainPage")
                    }else {
                        navController.navigate("startingScreenForQuiz")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 36.dp, start = 16.dp)
                    .size(width = 70.dp, height = 70.dp)
            ) {
            }
        }

    }
}



 */




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController
){
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(100.dp)
                .background(Color(0xFFE55655))
                .padding(top = 20.dp)
                .padding(horizontal = 5.dp)

        ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE55655)
                ),
                title = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Inner yellow color
                        Text(
                            text = "PokéDex",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.pokedexfont)),
                                fontSize = 27.sp,
                                color = Color(0xFFFFD88E)
                            ),

                            )
                        // Outline blue color
                        Text(
                            text = "PokéDex",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.pokedexfont)),
                                fontSize = 27.sp,
                                color = Color(0xFF000587),
                                drawStyle = Stroke(
                                    miter = 10f,
                                    width = 5f,
                                    join = StrokeJoin.Round
                                )
                            )
                        )
                        // Shadow
                        Text(
                            text = "PokéDex",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.pokedexfont)),
                                fontSize = 27.sp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        Color.Transparent
                                    ),
                                ),
                            )
                        )


                    }
                },

                navigationIcon = {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(55.dp)
                    ) {
                        // Button with border and base color
                        Button(
                            onClick = {
                                if(navController.currentDestination?.route == "pokemonPage/{pokemonName}") {
                                    navController.navigate("mainPage")
                                }else {
                                    navController.navigate("startingScreenForQuiz")
                                }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB5D4)),
                            modifier = Modifier
                                .matchParentSize()
                        ) { }

                        if (navController.currentDestination?.route != "mainPage") {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Go Back",
                                tint = Color.White, // Ensure the arrow is visible
                                modifier = Modifier
                                    .size(40.dp) // Icon size
                            )
                        }

                        // Overlay for shiny effect
                        Canvas(
                            modifier = Modifier.matchParentSize()
                                .border(3.dp, Color(0xFF610003), CircleShape) // Add a red border

                        ) {
                            drawCircle(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.8f), // Inner shiny effect
                                        Color.White.copy(alpha = 0.0f),
                                        Color.Black.copy(alpha = 0.6f) // Fade outward
                                    ),
                                    start = Offset(0f, 0f), // Top-left for shine
                                    end = Offset(size.width, size.height) // Bottom-right for shadow
                                ),
                                radius = size.minDimension / 2
                            )
                        }

                    }

                },


                actions = {
                    CircleDot(
                        sizeOfDot = 12,
                        dotColorInner = Color.Red
                    )
                    CircleDot(
                        sizeOfDot = 12,
                        dotColorInner = Color.Yellow
                    )
                    CircleDot(
                        sizeOfDot = 12,
                        dotColorInner = Color.Green
                    )

                },
            )
        }
    }
}


@Composable
fun CircleDot(sizeOfDot: Int, dotColorInner: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(sizeOfDot.dp).padding(1.dp)// Button size
    ) {
        // Button with border and base color
        Button(
            onClick = { /* Handle click */ },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = dotColorInner),
            modifier = Modifier
                .matchParentSize()
        ) { }

        // Overlay for shiny effect
        Canvas(
            modifier = Modifier.matchParentSize()
                .border(2.dp, Color(0xFF610003), CircleShape) // Add a red border

        ) {
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f), // Inner shiny effect
                        Color.White.copy(alpha = 0.0f),
                        Color.Black.copy(alpha = 0.6f) // Fade outward
                    ),
                    start = Offset(0f, 0f), // Top-left for shine
                    end = Offset(size.width, size.height) // Bottom-right for shadow
                ),
                radius = size.minDimension / 2
            )
        }

    }
}

@Preview
@Composable
fun TopBarPreview() {
        TopBar(
            navController = rememberNavController()
        )
}


