package com.example.skycheck

sealed class Screens(val route: String)
{
    data object MainScreen : Screens ("mainScreen")
    data object SearchScreen  : Screens ("searchScreen")
    data object FavoritesScreen : Screens ("favoritesScreen")
    data object SettingsScreen  : Screens ("settingsScreen")
}