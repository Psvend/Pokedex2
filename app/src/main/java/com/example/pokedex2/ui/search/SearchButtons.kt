package com.example.pokedex2.ui.search


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokedex2.viewModel.SearchViewModel

@Composable
fun ActionButtons(viewModel: SearchViewModel, modifier: Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { viewModel.selectAll() },
            modifier = modifier
        ) {
            Text(text = "Show all types")
        }
        Button(onClick = { viewModel.validateCriteria() },
            modifier = modifier
        ){
            Text(text = "Confirm")
        }
    }
}
