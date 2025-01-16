package com.example.pokedex2

//import com.example.pokedex2.ui.theme.PokemonDetailScreen
//import com.example.pokedex2.ui.theme.PokemonPage2
//import com.example.pokedex2.viewModel.PokemonPageViewModel
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokedex2.ui.MenuBar.MenuBar
import com.example.pokedex2.ui.Navigation.NavGraph
import com.example.pokedex2.ui.Navigation.NavGraph2
import com.example.pokedex2.ui.SearchAndFilters.TypeFilterUI
import com.example.pokedex2.ui.TopBar.TopBar
import com.example.pokedex2.ui.theme.Pokedex2Theme
import com.example.pokedex2.utils.RotatingLoader
import com.example.pokedex2.viewModel.MenuBarViewModel
import com.example.pokedex2.viewModel.PokeViewModel
import com.example.pokedex2.viewModel.TopBarViewModel
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector

)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pokedex2Theme {
                val navController = rememberNavController()
                val topBarViewModel: TopBarViewModel = viewModel()
                val menuBarViewModel: MenuBarViewModel = viewModel()
                val pokeViewModel: PokeViewModel = hiltViewModel() // Injecting PokeViewModel
                val selectedItemIndex by menuBarViewModel.selectedItemIndex.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            viewModel = topBarViewModel,
                            navController = navController
                        )

                    },
                    bottomBar = {
                        MenuBar(
                          viewModel = menuBarViewModel
                        )
                    }
                ) { innerPadding ->
                    when(selectedItemIndex){
                        0 ->  NavGraph(navController = navController,
                            startDestination = "mainPage" )

                       // 1 -> PokemonPage(pokemonPageViewModel = PokePageViewModel )

                        2 -> NavGraph2(navController = navController,
                            startDestination = "startingScreenForQuiz")
                        3 -> TypeFilterUI(modifier = Modifier.padding(innerPadding))
                        else -> RotatingLoader()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTopBar(){

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(100.dp)
                .background(Color(0xFFE55655))
                .padding(horizontal = 10.dp)
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
                            onClick = { /* Handle click */ },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB5D4)),
                            modifier = Modifier
                                .matchParentSize()
                        ) { }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Go Back",
                            tint = Color.White, // Ensure the arrow is visible
                            modifier = Modifier
                                .size(40.dp) // Icon size
                        )

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
fun NewTopBarPreview() {
    Pokedex2Theme {
        NewTopBar()
    }
}

