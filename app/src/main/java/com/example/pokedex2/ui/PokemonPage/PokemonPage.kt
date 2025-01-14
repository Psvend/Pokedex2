package com.example.pokedex2.ui.PokePage


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex2.ui.PokemonList.PokemonTypeIcons
import com.example.pokedex2.ui.SearchAndFilters.capitalizeFirstLetter
import com.example.pokedex2.viewModel.PokePageViewModel


@Composable
fun PokemonPage(
    pokemonIdOrName: String,
    modifier: Modifier = Modifier,
    viewModel: PokePageViewModel = hiltViewModel()
) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val pokemonLocations by viewModel.pokemonLocations.collectAsState()
    val abilities by viewModel.abilities.collectAsState()
    val growthRate by viewModel.growthRate.collectAsState()
    val evolvesTo by viewModel.evolvesTo.collectAsState()

    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonAbilities(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonSpecies(pokemonIdOrName.lowercase())
        viewModel.fetchEvolutionChain(pokemonIdOrName.lowercase())

    }

    // Fetch encounter locations
    LaunchedEffect(pokemonDetail?.location_area_encounters) {
        pokemonDetail?.location_area_encounters?.let { url ->
            viewModel.fetchPokemonEncounters(url)
        }
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
                .height(140.dp)
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
                .height(12.dp) // Adjust the height to control the space
                .background(Color.Transparent) //To test if its being added
        )



        PokemonImage(model = pokemonDetail?.sprites?.front_default)


        pokemonDetail?.types?.map { it.type.name }?.let { types ->
            PokemonTypeIcons(types = types)
        }


        Spacer(modifier = Modifier.height(20.dp))


        PokemonLocation(locations = pokemonLocations)

        Spacer(modifier = Modifier.height(20.dp))

        PokemonAbilities(abilities = abilities)

        Spacer(modifier = Modifier.height(20.dp))

        PokemonGrowthRate(growthRate = growthRate, viewModel = viewModel)

        Spacer(modifier = Modifier.height(20.dp))

        PokemonEvolvesTo(evolvesTo = evolvesTo)

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
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 4.dp)
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
fun PokemonAbilities(abilities: List<String>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Abilities",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (abilities.isEmpty()) {
            Text(
                text = "No abilities available",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            abilities.forEach { ability ->
                Text(
                    text = ability.capitalizeFirstLetter(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Composable
fun PokemonGrowthRate(growthRate: String, viewModel: PokePageViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        // Title
        Text(
            text = "Growth Rate",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Progress Bar with Label
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Progress Bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp)
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                // Fetch progress and color
                val progress = viewModel.getGrowthRateProgress(growthRate)
                val color = viewModel.getGrowthRateColor(growthRate)

                // Log for debugging
                Log.d("PokemonGrowthRate", "Progress: $progress, Color: $color")

                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(color, shape = RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Growth Rate Label
            Text(
                text = growthRate.capitalizeFirstLetter(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
        }
    }
}

@Composable
fun PokemonEvolvesTo(evolvesTo: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Evolves To",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (evolvesTo.isEmpty() || (evolvesTo.size == 1 && evolvesTo[0] == "Nothing, this is the max evolution step")) {
            Text(
                text = "Nothing, this is the max evolution step",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            evolvesTo.forEach { evolution ->
                Text(
                    text = evolution,
                    style = MaterialTheme.typography.bodyMedium,
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











