package com.example.skycheck

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
    fun NavGraph(nav: NavHostController, viewModel: FavouriteViewModel, isDarkTheme: MutableState<Boolean>, themePreferenceManager: ThemePreferenceManager) {
        NavHost(
            navController = nav,
            startDestination = Screens.MainScreen.route
        ) {
            composable(route = Screens.MainScreen.route) {
                MainScreen(nav = nav, viewModel = viewModel, isDarkTheme)
            }
            composable(route = Screens.SearchScreen.route) {
                SearchScreen(nav = nav)
            }
            composable(route = Screens.FavoritesScreen.route) {
                FavouritesScreen(nav = nav, viewModel = viewModel)
            }
            composable(Screens.SettingsScreen.route) {
                SettingsScreen(
                    nav = nav,
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme,
                    themePreferenceManager = themePreferenceManager
                )
            }
            composable("weatherDetailsScreen/{city}") { backStackEntry ->
                val city = backStackEntry.arguments?.getString("city") ?: "Unknown"
                WeatherDetailsScreen(nav = nav, city = city, viewModel = viewModel)
            }
            composable(
                "detailFav/{cityId}",
                arguments = listOf(navArgument("cityId") { type = NavType.IntType })
            ) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
                FavouriteDetail(nav, viewModel, cityId)
            }
            composable("forecast/{city}") { backStackEntry ->
                val city = backStackEntry.arguments?.getString("city") ?: "Unknown"
                ForecastScreen(city, isDarkTheme)
            }
        }
    }
