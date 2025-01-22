package com.example.pokedex2.ui.MenuBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Star
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

@Composable
fun MenuBar(
    viewModel: MenuBarViewModel = viewModel()
) {
    val selectedItemIndex by viewModel.selectedItemIndex.collectAsState()
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Favorites", Icons.Filled.FavoriteBorder, Icons.Outlined.FavoriteBorder),
        BottomNavItem("Quiz", Icons.Filled.Star, Icons.Outlined.Star),
    )

    val backgroundColor = if (selectedItemIndex == 2) Color(0xFFE55655).copy(alpha = 0.9F) else Color(0xFFE55655).copy(alpha = 0.9f)
    val unselectedColor = if (selectedItemIndex == 2) Color(0xFF610003) else Color(0xFF610003)
    val selectedColor =  if (selectedItemIndex == 2) Color(0xFFFFD88E) else Color(0xFFFFD88E)

    NavigationBar(
        containerColor = backgroundColor,
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