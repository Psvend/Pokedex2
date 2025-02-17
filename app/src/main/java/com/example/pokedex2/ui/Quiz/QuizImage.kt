package com.example.pokedex2.ui.Quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun QuizImage(model : String?, isClear: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .height(260.dp)
                .width(260.dp)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (model != null) {
                AsyncImage(
                    model = model,
                    contentDescription = "{pokemonDetail?.name} sprite",
                    colorFilter = if(isClear)null else ColorFilter.tint(Color.Black),
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
        }
    }
}