package com.example.skycheck

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(nav: NavHostController, viewModel: FavouriteViewModel, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { BottomMenu(nav = nav) },
        content = { NavGraph(nav = nav, viewModel = viewModel) }
    )
}
