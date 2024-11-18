package com.example.pokedex2.ui.menuNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex2.BottomNavItem
import com.example.pokedex2.viewModel.MenuBarViewModel

/*
data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
 */


@Composable
fun MenuBar(
    viewModel: MenuBarViewModel = viewModel()
) {
    val selectedItemIndex by viewModel.selectedItemIndex.collectAsState()
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Favorites", Icons.Filled.FavoriteBorder, Icons.Outlined.FavoriteBorder),
        BottomNavItem("Search", Icons.Filled.Search, Icons.Outlined.Search),
        BottomNavItem("Filter", Icons.Filled.Edit, Icons.Outlined.Edit)
    )

    // Determine the background color based on the selected index for custom searchView..
    val backgroundColor = if (selectedItemIndex == 2) Color(0xFF000000).copy(alpha = 0.95F) else Color(
        0xFFE55655
    ).copy(alpha = 0.9f)
    val unselectedColor = if (selectedItemIndex == 2) Color(0xFF610003) else Color(0xFF610003)
    val selectedColor =  if (selectedItemIndex == 2) Color(0xFFFFD88E) else Color(0xFFFFD88E)
    NavigationBar(
        containerColor = backgroundColor, // Set the NavigationBar background color to have a custom SearchView..
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedItemIndex
            val contentColor = if (isSelected) selectedColor else unselectedColor
            NavigationBarItem(
                selected = index == selectedItemIndex,
                onClick = { viewModel.selectItem(index) },
                label = {
                    Text(
                        text = item.title,
                        color = contentColor
                    )
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = contentColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White.copy(alpha = 0.0F)
                )

            )
        }
    }
}






//OLD CODE (DO NOT DELETE) - PETRINE
    /*
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Scaffold( bottomBar = {
            NavigationBar {
                item.forEachIndexed { index, item -> NavigationBarItem(
                    selected = index == selectedItemIndex,
                    onClick = { selectedItemIndex = index },
                    label = { item.title },
                    icon = {
                        Icon(
                            imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )

                    }
                )
                }
            }
        }
        ) {

        }
    }
}

*/