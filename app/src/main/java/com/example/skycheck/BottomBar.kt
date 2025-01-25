package com.example.skycheck

sealed class BottomBar(
    val route: String,
    val title: String
) {
    data object MainScreen : BottomBar(Screens.MainScreen.route, "Ekran Główny")
    data object SearchScreen : BottomBar(Screens.SearchScreen.route, "Wyszukiwanie")
    data object FavoritesScreen : BottomBar(Screens.FavoritesScreen.route, "Ulubione")
    data object SettingsScreen : BottomBar(Screens.SettingsScreen.route, "Ustawienia")
}
