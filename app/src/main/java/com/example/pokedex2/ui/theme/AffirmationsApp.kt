package com.example.pokedex2.ui.theme
import com.example.pokedex2.model.affirmation
import com.example.pokedex2.data.DatasourcePokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment



@Composable
fun AffirmationsApp(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE55655))

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .background(Color(0xFFE55655))
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_bar_background), // Replace with your image name
                contentDescription = "Top Bar Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {

            val layoutDirection = LocalLayoutDirection.current
            Surface(
                modifier = modifier
                    .statusBarsPadding()
                    .padding(
                        start = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                    )
            ) {
                AffirmationsList(
                    affirmationLIST = DatasourcePokemon().loadAffirmations()
                )
            }
        }
    }
}



@Composable
fun AffirmationsList(affirmationLIST: List<affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFD9D9D9))

    ) {
        items(affirmationLIST) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}


@Composable
fun AffirmationCard(affirmation: affirmation, modifier: Modifier = Modifier) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFF9E6)),
        shape = RectangleShape

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            //pokemon image
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            //text and category icons in the middle
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                //Name of pokemon
                Text(
                    text = LocalContext.current.getString(affirmation.stringResourceId),
                    //modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )

                //Row for dynamic pokemon type text
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    //Our category icons
                    affirmation.typeIcon.forEach { typeIcon ->
                        Text (
                            text = typeIcon,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }


            //The like button and number



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 8.dp)
            ) {
            IconButton(onClick = { isLiked = !isLiked }) {
                Icon(
                    painter = painterResource(
                        if (isLiked) R.drawable.heart_filled else R.drawable.heart_empty
                    ),
                    contentDescription = if (isLiked) "Unlike" else "Like",
                    tint = if (isLiked) Color(0xFFB11014) else Color(0xFFB11014)
                )
            }
            //Id number of pokemon
                Text (
                    text = affirmation.number,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            //Spacer to make space
            //Spacer(modifier = Modifier.weight(1f))


        }
    }
}


/*
@Composable
fun AffirmationCard(affirmation: affirmation, modifier: Modifier = Modifier){
    Card(modifier = modifier){
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }

    }
}
*/