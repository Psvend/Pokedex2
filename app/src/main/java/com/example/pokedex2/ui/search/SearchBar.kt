package com.example.pokedex2.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex2.viewModel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(viewModel: SearchViewModel) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onFocusChanged { focusState ->
                if (focusState.isFocused && viewModel.searchQuery.value == "Name, number or description") {
                    viewModel.searchQuery.value = ""
                } else {
                    viewModel.searchQuery.value = "Name, number or description"
                }
            },
        query = viewModel.searchQuery.value,
        onQueryChange = { viewModel.searchQuery.value = it },
        onSearch = {},
        active = viewModel.acceptSearchCriteria.value,
        onActiveChange = { viewModel.active.value = it },
        trailingIcon = {
            IconButton(onClick = { viewModel.clearSearch() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear Search",
                    tint = Color.Gray
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            if (viewModel.searchQuery.value.isNotBlank()) {
                Text(
                    text = "Search Query: ${viewModel.searchQuery.value}",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
