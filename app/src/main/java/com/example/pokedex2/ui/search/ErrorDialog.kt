package com.example.pokedex2.ui.search

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.pokedex2.viewModel.SearchViewModel

@Composable
fun ErrorDialog(viewModel: SearchViewModel) {
    if (viewModel.showDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.showDialog.value = false },
            confirmButton = {
                Button(onClick = { viewModel.showDialog.value = false }) {
                    Text("OK")
                }
            },
            title = { Text(text = "No Search Criteria") },
            text = { Text(text = "Nothing has been chosen") }
        )
    }
}
