package com.example.pokedex2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.pokedex2.model.Affirmation
import androidx.compose.ui.Modifier
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import com.example.pokedex2.R
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedex2.viewModel.PokemonPageViewModel
import com.example.pokedex2.data.DatasourcePokemon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import com.example.pokedex2.ui.*
import com.example.pokedex2.model.*
import com.example.pokedex2.data.*
import com.example.pokedex2.ui.theme.PokemonImage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding


@Composable
fun EvolutionPager(
    evolutions: List<Affirmation>, // The list of Pokémon evolutions
    currentEvolution: Int,         // The currently displayed evolution (passed from ViewModel or parent)
    onPageChanged: (Int) -> Unit   // Callback to notify when the page changes
) {
    // Remember the state of the pager
    val pagerState = rememberPagerState(
        initialPage = currentEvolution,
        pageCount = {3}
    )

    // Notify the parent when the user swipes to a different page
    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Horizontal Pager for swiping through Pokémon evolutions
        HorizontalPager(
            state = pagerState,
            //pageCount = 3,
            modifier = Modifier
                .fillMaxSize() // Adjust size as needed
        ) { page ->
            // Render the Pokémon image for each evolution
            PokemonImage(affirmation = evolutions[page])
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dots Indicator
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            evolutions.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 12.dp else 8.dp) // Current dot is larger
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) Color.Yellow else Color.Gray
                        )
                )
            }
        }
    }
}