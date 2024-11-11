package com.example.pokedex2.ui.theme
import com.example.pokedex2.model.affirmation
import com.example.pokedex2.data.DatasourcePokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.pokedex2.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun AffirmationsApp(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = modifier
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection),
            )
    ) {
        AffirmationsList(
            affirmationLIST = DatasourcePokemon().loadAffirmations()
        )
    }
}


@Composable
fun AffirmationsList(affirmationLIST: List<affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
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

    Card(modifier = modifier.padding(4.dp)) {
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
                //Id number of pokemon
                Row (
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    ) {
                    //showing number here
                    Text (
                        text = affirmation.number,
                        style = MaterialTheme.typography.bodySmall
                    )
                }


                //Row for dynamic pokemon type images
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    //modifier = Modifier.padding(top = 1.dp)
                ) {
                    //Our category icons
                    affirmation.typeIcons.forEach { typeIcon ->
                        Image(
                            painter = painterResource(id = typeIcon),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }


            //The like button
            IconButton(onClick = { isLiked = !isLiked }) {
                Icon(
                    painter = painterResource(
                        if (isLiked) R.drawable.heart_filled else R.drawable.heart_empty
                    ),
                    contentDescription = if (isLiked) "Unlike" else "Like",
                    tint = if (isLiked) Color.Red else Color.Gray
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