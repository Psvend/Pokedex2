package com.example.pokedex2.ui.PokePage


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex2.viewModel.PokePageViewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pokedex2.model.Affirmation


@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    viewModel: PokePageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier)
{
    //Add new items here to show
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    //val pokemonImage by viewModel.pokemonImage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9E6))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Top Section - Name and Number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName(name = pokemonDetail?.name ?: "Unknown")
            //PokemonNr()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center Image and Like Button
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PokemonImage(model = pokemonDetail?.sprites?.front_default)

            LikeButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        /*
        // Types Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 1.dp),

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonType(affirmation = pokemonAffirmation)
        }

     */

        Spacer(modifier = Modifier.height(16.dp))

        // Description Section
        //PokemonDescription(affirmation = pokemonAffirmation)

        Spacer(modifier = Modifier.height(16.dp))

        //Graph section
        //PokemonStats(affirmation = pokemonAffirmation)


    }
}




@Composable
fun PokemonName(name: String) {
    Text(
        text = name ?: "Loading...",
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.Default
        ),
        color = Color.DarkGray
    )
}

/*

@Composable
fun PokemonNr(){
    Text(
        //text = pokemonDetail?.
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily.Default
        ),
        color = Color.DarkGray
    )
}


 */

@Composable
fun PokemonImage(model: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = "{pokemonDetail?.name} sprite",
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
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


    /*
@Composable
fun PokemonType(affirmation: Affirmation) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
    ) {

        //Our category icons
        affirmation.typeIcon.forEach { typeIcon ->
            Image(
                painter = painterResource(typeIcon),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .width(50.dp)
                    .height(20.dp)
                    .padding(2.dp) //space between the categories
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .size(30.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}




@Composable
fun PokemonDescription(affirmation: Affirmation){
    Text(
        text = affirmation.description,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily.Default
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    )
}


//The graph section
@Composable
fun PokemonStats(affirmation: Affirmation){
    Box (
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Image(
            painter = painterResource(affirmation.graph),
            contentDescription = "Graph",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.CenterStart)

        )
    }
}
*/

    //Reuse of likebutton
    @Composable
    fun LikeButton(modifier: Modifier = Modifier) {
        var isSelect by remember { mutableStateOf(false) }
        val iconModifier = Modifier
            .size(40.dp)
            .clickable { isSelect = !isSelect }

        Box(modifier = Modifier) {
            if (isSelect) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 110.dp, y = -85.dp)
                        .clickable { isSelect = !isSelect }
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Unlike",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 110.dp, y = -85.dp)
                        .clickable { isSelect = !isSelect }
                )
            }
        }
    }




/*

@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    viewModel: PokePageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    //Add new items here to show
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonImage by viewModel.pokemonImage.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
    }


    //UI box to handle name  from api
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(120.dp),
        contentAlignment = Alignment.TopStart
    ) {
        when {
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            pokemonDetail != null -> {
                //Show the name of the pokemon from api
                Text(
                    text = pokemonDetail?.name ?: "Loading...",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }

            else -> {
                CircularProgressIndicator()
            }

        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // UI box to handle image
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            pokemonDetail != null -> {
                //Show image from api
                AsyncImage(
                    model = pokemonDetail?.sprites?.front_default,
                    contentDescription = "{pokemonDetail?.name} sprite",
                    modifier = Modifier.size(500.dp)
                )
            }

            else -> {
                CircularProgressIndicator()
            }
        }
    }


}


 */













