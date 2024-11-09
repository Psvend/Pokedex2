package com.example.pokedex2.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex2.BottomNavItem

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector

)
@Composable
fun MenuBar(){
    var item = listOf(
        BottomNavItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Farvorites", Icons.Filled.FavoriteBorder, Icons.Outlined.FavoriteBorder),
        BottomNavItem("Search", Icons.Filled.Search, Icons.Outlined.Search),
        BottomNavItem("Filter", Icons.Filled.Edit, Icons.Outlined.Edit),
    )
    var selectedIremIndex by rememberSaveable { mutableStateOf(0) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Scaffold( bottomBar = {
            NavigationBar {
                item.forEachIndexed { index, item -> NavigationBarItem(
                    selected = index == selectedIremIndex,
                    onClick = { selectedIremIndex = index },
                    label = { item.title },
                    icon = {
                        Icon(
                            imageVector = if (selectedIremIndex == index) item.selectedIcon else item.unselectedIcon,
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