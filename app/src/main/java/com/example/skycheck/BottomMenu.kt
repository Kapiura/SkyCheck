package com.example.skycheck

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomMenu(nav: NavHostController) {
    val screens = listOf(
        BottomBar.MainScreen,
        BottomBar.SearchScreen,
        BottomBar.FavoritesScreen,
        BottomBar.SettingsScreen
    )

    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    nav.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen) {
                        BottomBar.MainScreen -> Icon(Icons.Default.Home, contentDescription = "Ekran Główny")
                        BottomBar.SearchScreen -> Icon(Icons.Default.Search, contentDescription = "Wyszukaj")
                        BottomBar.FavoritesScreen -> Icon(Icons.Default.Favorite, contentDescription = "Ulubione")
                        BottomBar.SettingsScreen -> Icon(Icons.Default.Settings, contentDescription = "Ustawienia")
                    }
                },
                enabled = true,
                alwaysShowLabel = true
            )
        }
    }
}


