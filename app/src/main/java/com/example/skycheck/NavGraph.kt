package com.example.skycheck

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import java.lang.reflect.Modifier

// NavGraph.kt
@Composable
fun NavGraph(nav: NavHostController, viewModel: FavouriteViewModel) {
    NavHost(
        navController = nav,
        startDestination = Screens.MainScreen.route
    ) {
        composable(route = Screens.MainScreen.route) {
            MainScreen(nav = nav, viewModel = viewModel)
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(nav = nav)
        }
        composable(route = Screens.FavoritesScreen.route) {
            FavouritesScreen(nav = nav, viewModel = viewModel)
        }
        composable("weatherDetailsScreen/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: "Unknown"
            WeatherDetailsScreen(city = city, viewModel)
        }
        composable(
            "detailFav/{cityId}",
            arguments = listOf(navArgument("cityId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cityId = backStackEntry.arguments?.getInt("cityId") ?: 0
            FavouriteDetail(nav, viewModel, cityId)
        }
    }
}

