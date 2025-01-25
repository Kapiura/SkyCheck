package com.example.skycheck

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgument

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    nav: NavHostController,
    viewModel: FavouriteViewModel,
    isDarkTheme: MutableState<Boolean>,  // Dodanie argumentu isDarkTheme
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = { BottomMenu(nav = nav) },
        content = {
            NavHost(navController = nav, startDestination = Screens.MainScreen.route) {
                composable(Screens.MainScreen.route) {
                    MainScreen(nav = nav, viewModel = viewModel, isDarkTheme = isDarkTheme) // Przekazanie isDarkTheme
                }
                composable(Screens.SearchScreen.route) {
                    SearchScreen(nav = nav) // Przykład, możesz dodać więcej ekranów
                }
                composable(Screens.FavoritesScreen.route) {
                    FavouritesScreen(nav = nav, viewModel = viewModel) // Ekran ulubionych
                }
                composable(Screens.SettingsScreen.route) {
                    SettingsScreen(
                        nav = nav,
                        viewModel = viewModel,  // Przekazanie ViewModelu
                        isDarkTheme = isDarkTheme,
                    )
                }
                composable("weatherDetailsScreen/{city}") { backStackEntry ->
                    val city = backStackEntry.arguments?.getString("city") ?: "Unknown"
                    WeatherDetailsScreen(nav = nav, city = city, viewModel = viewModel) // Ekran szczegółów pogody
                }
                composable(
                    "detailFav/{cityId}",
                    arguments = listOf(navArgument("cityId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
                    FavouriteDetail(nav, viewModel, cityId) // Ekran szczegółów ulubionego miasta
                }
                composable("forecast/{city}") { backStackEntry ->
                    val city = backStackEntry.arguments?.getString("city") ?: "Unknown"
                    ForecastScreen(city,isDarkTheme) // Ekran prognozy pogody
                }
            }
        }
    )
}
