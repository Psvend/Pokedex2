package com.example.pokedex2.ui.SearchAndFilters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterOverlay(
    showOverlay: Boolean,
    onClose: () -> Unit
) {

    if(showOverlay){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .wrapContentSize(align = Alignment.Center)
        ) {
            Column (
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                    .padding(bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {onClose()},
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = Color.DarkGray,
                            contentDescription = "Close FilterOverlay",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

            }
        }
    }
}