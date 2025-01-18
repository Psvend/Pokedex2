package com.example.pokedex2.ui.PokePage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex2.R
import com.example.pokedex2.data.remote.EvolutionDetailUI
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
    val stats by viewModel.pokemonStats.collectAsState()
    val characteristicDescription by viewModel.characteristicDescription.collectAsState()

    // Map the evolution data from your API into EvolutionDetailUI objects
    val evolutionDetailsUI = evolvesTo.map { evolution ->
        EvolutionDetailUI(
            name = evolution.name.capitalizeFirstLetter(),
            imageUrl = evolution.imageUrl, // URL to the Pokémon's sprite
            requirement = evolution.requirement // "Level 22" or "High Friendship"
        )
    }


    // Fetch Pokémon details when the page is displayed
    LaunchedEffect(pokemonIdOrName) {
        viewModel.fetchPokemonDetail(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonAbilities(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonSpecies(pokemonIdOrName.lowercase())
        viewModel.fetchEvolutionChain(pokemonIdOrName.lowercase())
        viewModel.fetchPokemonStats(pokemonIdOrName.lowercase())
    }

    //Fetch pokemon description
    LaunchedEffect(pokemonDetail?.id) {
        pokemonDetail?.id?.let { id ->
            viewModel.fetchCharacteristic(id)
        }
    }

    // Fetch encounter locations
    LaunchedEffect(pokemonDetail?.location_area_encounters) {
        pokemonDetail?.location_area_encounters?.let { url ->
            viewModel.fetchPokemonEncounters(url)
        }
    }

    Box(
        modifier = Modifier
            .background(Color(0xffeae6d5)) // Set the full background color here
    ) {
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
                PokemonTypeIcons(types = types, fontSize = 10)
            }


            Spacer(modifier = Modifier.height(20.dp))

            PokemonDescription(description = characteristicDescription)

            Spacer(modifier = Modifier.height(20.dp))


            PokemonLocation(locations = pokemonLocations)

            Spacer(modifier = Modifier.height(15.dp))

            PokemonAbilities(abilities = abilities)

            Spacer(modifier = Modifier.height(15.dp))

            PokemonGrowthRate(growthRate = growthRate, viewModel = viewModel)

            Spacer(modifier = Modifier.height(15.dp))

            //PokemonEvolvesTo(evolvesTo = evolutionDetailsUI)
            PokemonEvolvesTo(
                evolvesTo = evolutionDetailsUI,
                currentPokemon = EvolutionDetailUI(
                    name = pokemonDetail?.name?.capitalizeFirstLetter() ?: "Unknown",
                    imageUrl = pokemonDetail?.sprites?.front_default ?: "",
                    requirement = "This is the final form"
                )
            )


            Spacer(modifier = Modifier.height(15.dp))

            PokemonStatsGraph(stats = stats, viewModel = viewModel)


            Spacer(
                modifier = Modifier
                    .fillMaxWidth() // Ensure the spacer spans the width of the column
                    .height(200.dp) // Adjust the height to control the space
                    .background(Color.Transparent) //To test
            )

        }
    }
}


@Composable
fun PokemonName(name: String) {
    Column {
        Text(
            text = name.capitalizeFirstLetter() ?: "Loading...",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                color = Color.DarkGray
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
                //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
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

        var isLiked by remember { mutableStateOf(false) }
        // LikeButton added directly inside the Box
        LikeButton(
            isLiked = isLiked,
            onLikeClicked = {isLiked = !isLiked},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-25).dp, y = 25.dp) //edit the position of the likebutton inside the box of the pokemon image
        )
    }
}


@Composable
fun PokemonDescription(
    title: String = "Characteristic",
    description: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Characteristic",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray
                )
            )
        }
    }
}




@Composable
fun PokemonLocation(locations: List<String>) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Encounters",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (locations.isEmpty()) {
                Text(
                    text = "This pokemon don't have specific encounter locations",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray
                        //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                    ),
                    textAlign = TextAlign.Start
                )
            } else {
                locations.forEach { location ->
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray
                            //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}




@Composable
fun PokemonAbilities(abilities: List<String>) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Abilities",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (abilities.isEmpty()) {
                Text(
                    text = "No abilities available",
                    style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray
                    //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                    ),
                )
            } else {
                abilities.forEach { ability ->
                    Text(
                        text = ability.capitalizeFirstLetter(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.DarkGray
                            //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun PokemonGrowthRate(growthRate: String, viewModel: PokePageViewModel) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
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
                    color = Color.DarkGray,
                    //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
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
                        //fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                    )
                )
            }
        }
    }
}

@Composable
fun PokemonEvolvesTo(evolvesTo: List<EvolutionDetailUI>, currentPokemon: EvolutionDetailUI, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Evolution",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (evolvesTo.isEmpty() || (evolvesTo.size == 1 && evolvesTo[0].name == "Max Evolution")) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image of the max evolution
                    AsyncImage(
                        model = currentPokemon.imageUrl, // Use the current Pokémon's image
                        contentDescription = "Max evolution sprite",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Max Evolution Details
                    Column {
                        Text(
                            text = currentPokemon.name, // Use the current Pokémon's name
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                        )

                        Text(
                            text = "This is the final form",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                    }
                }
            } else {
                evolvesTo.forEach { evolution ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Evolution Image
                        AsyncImage(
                            model = evolution.imageUrl,
                            contentDescription = "${evolution.name} sprite",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Evolution Details
                        Column {
                            Text(
                                text = evolution.name,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            )

                            Text(
                                text = evolution.requirement,
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun PokemonStatsGraph(stats: List<Pair<String, Int>>, viewModel: PokePageViewModel) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(Color(0xFFf0ecdd), shape = RoundedCornerShape(12.dp)) // Add a rounded box
            .padding(16.dp) // Inner padding within the rounded box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stats",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    //fontFamily = FontFamily(Font(R.font.pressstart2p_regular))
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            stats.forEach { (name, value) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium, //.copy(fontFamily = FontFamily(Font(R.font.pressstart2p_regular))),
                        modifier = Modifier.padding(bottom = 4.dp),
                        color = Color.DarkGray
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                    ) {
                        val progress = value / 255f // Scale to [0, 1]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .fillMaxHeight()
                                .background(
                                    viewModel.getStatColor(name),
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                    }

                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.bodySmall, //.copy(fontFamily = FontFamily(Font(R.font.pressstart2p_regular))),
                        modifier = Modifier.align(Alignment.End),
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    onLikeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() } // To disable ripple effect

    Icon(
        painter = painterResource(
            id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_empty
        ),
        contentDescription = if (isLiked) "Unlike" else "Like",
        tint = Color(0xFFB11014), // Set the desired tint color
        modifier = modifier
            .size(40.dp) // Adjust size as needed
            .clickable(
                onClick = onLikeClicked,
                interactionSource = interactionSource,
                indication = null // Disable the ripple effect
            )
    )
}




/*
@Composable
fun LikeButton(
    isLiked: Boolean,
    onLikeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(
        onClick = onLikeClicked,
        modifier = modifier
            .size(40.dp) // Adjust size as needed

    ) {
        Icon(
            painter = painterResource(
                id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_empty
            ),
            contentDescription = if (isLiked) "Unlike" else "Like",
            tint = Color(0xFFB11014) // Set the desired tint color
        )
    }
}


 */
/*

@Composable
fun LikeButton(modifier: Modifier = Modifier) {
    var isSelect by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier) {
        if (isSelect) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Like",
                tint = Color(0xFFB11014),
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null // Disable the ripple effect
                    ) { isSelect = !isSelect }
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Unlike",
                tint = Color(0xFFB11014),  //Color.Gray
                modifier = Modifier
                    .size(40.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null // Disable the ripple effect
                    ) { isSelect = !isSelect }
            )
        }
    }
}



 */


