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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.pokedex2.model.Affirmation
import com.example.pokedex2.ui.PokemonList.PokemonTypeIcons
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter

@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    modifier: Modifier = Modifier,
    viewModel: PokePageViewModel = hiltViewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonLocations by viewModel.pokemonLocations.collectAsState()
    val pokemonForm by viewModel.pokemonForm.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase
    }

    // Fetch encounter locations
    LaunchedEffect(pokemonDetail?.location_area_encounters) {
        pokemonDetail?.location_area_encounters?.let { url ->
            viewModel.fetchPokemonEncounters(url)
        }
    }

    //Fetch info about pokemon forms
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonForm(pokemonIdOrName)
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp)
    ) {

        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )


        // Top Section - Name and Number
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName(name = pokemonDetail?.name ?: "Unknown")

            pokemonDetail?.id?.let { id ->
                PokemonNr(id = id)
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth() // Ensure the spacer spans the width of the column
                .height(16.dp) // Adjust the height to control the space
                .background(Color.Transparent) //To test if its being added
        )



        PokemonImage(model = pokemonDetail?.sprites?.front_default)


        pokemonDetail?.types?.map { it.type.name }?.let { types ->
            PokemonTypeIcons(types = types)
        }


        Spacer(modifier = Modifier.height(20.dp))


        PokemonLocation(locations = pokemonLocations)


        PokemonForms(forms = pokemonForm)





        Spacer(
            modifier = Modifier
                .fillMaxWidth() // Ensure the spacer spans the width of the column
                .height(200.dp) // Adjust the height to control the space
                .background(Color.Transparent) //To test
        )

    }
}





@Composable
fun PokemonName(name: String) {
    Column {
        Text(
            text = name.capitalizeFirstLetter() ?: "Loading...",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default
            ),
            color = Color.DarkGray
        )
    }
}



@Composable
fun PokemonNr(id: Int){
    Column {
        Text(
            text = "#$id" ?: "Loading...",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default
            ),
            color = Color.DarkGray
        )
    }
}

@Composable
fun PokemonImage(model: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Ensures the image stays centered horizontally
            .wrapContentHeight() // Ensures the box does not consume unnecessary vertical space
            .padding(vertical = 16.dp), // Adds some vertical spacing
        contentAlignment = Alignment.Center
    ) {
        Box(  //outer box
            modifier = Modifier
                .size(300.dp)
                .shadow(
                    16.dp,
                    shape = RoundedCornerShape(24.dp)
                ) // Updated shadow with rounded corners
                .border(16.dp, Color.Gray, shape = RoundedCornerShape(24.dp)) // Updated border
                .background(Color.White, shape = RoundedCornerShape(24.dp))
        ) {
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = "Pokemon sprite",
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(24.dp)) // Match the rounded corners
                )
            } else {
                Text(
                    text = "Image not available",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // LikeButton added directly inside the Box
        LikeButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-25).dp, y = 25.dp) //edit the position of the likebutton inside the box of the pokemon image
        )
    }
}



@Composable
fun PokemonLocation(locations: List<String>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Encounters",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (locations.isEmpty()) {
            Text(
                text = "No encounter data available",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default
                ),
                textAlign = TextAlign.Start
            )
        } else {
            locations.forEach { location ->
                Text(
                    text = location,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun PokemonForms(forms: List<String>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Pokemon Forms",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (forms.isEmpty()) {
            Text(
                text = "No data on Pokémon forms available",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default
                ),
                textAlign = TextAlign.Start
            )
        } else {
            forms.forEach { form ->
                Text(
                    text = form,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


















@Composable
fun LikeButton(modifier: Modifier = Modifier) {
    var isSelect by remember { mutableStateOf(false) }
    Icon(
        imageVector = if (isSelect) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = if (isSelect) "Like" else "Unlike",
        tint = if (isSelect) Color.Red else Color.Gray,
        modifier = modifier
            .size(40.dp)
            .clickable { isSelect = !isSelect }
    )
}



/*
@Composable
fun PokemonLocation(locations: List<String>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
            Text(
                text = "Encounters",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        if (locations.isEmpty()) {
                Text(
                    text = "No encounter data available",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default
                    ),
                    textAlign = TextAlign.Start
                )
        } else {
            locations.forEach { location ->
                Text(
                    text = location,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}



*/




//OLD WORKING CODE, NOT SCROLLABLE
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
    //val pokemonImage by viewModel.pokemonImage.collectAsState()
    val pokemonId by viewModel.pokemonId.collectAsState()
    val pokemonLocations by viewModel.pokemonLocations.collectAsState()
    val pokemonForms by viewModel.pokemonForms.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase()) // Ensure lowercase is passed
        //viewModel.fetchPokemonHabitat(pokemonIdOrName.lowercase())
    }

    // Fetch encounter locations when location_area_encounters is available
    LaunchedEffect(pokemonDetail?.location_area_encounters) {
        pokemonDetail?.location_area_encounters?.let { url ->
            viewModel.fetchPokemonEncounters(url)
        }
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
                .height(130.dp)
        )

        //Spacer(modifier = Modifier.height(2.dp))

        // Top Section - Name and Number
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PokemonName(name = pokemonDetail?.name ?: "Unknown")

            pokemonDetail?.id?.let { id ->
                PokemonNr(id = id)
            }
        }

        // Spacer(modifier = Modifier.height(16.dp))

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

        pokemonDetail?.types?.map { it.type.name }?.let { types ->
            PokemonTypeIcons(types = types)
        }

        //Pokemon Location Encounter
        PokemonLocation(locations = pokemonLocations)

    }
}
*/







