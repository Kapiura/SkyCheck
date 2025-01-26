package com.example.skycheck

import SettingsViewModel
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.ui.theme.SkyCheckTheme
import com.google.android.gms.location.FusedLocationProviderClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = FavouriteRepo(FavouriteDatabase.getDatabase(this).favDao())
        val viewModelFactory = FavouriteViewModelFactory(repo)
        val themePreferenceManager = ThemePreferenceManager(this) // Dodano ThemePreferenceManager

        setContent {
            val isDarkTheme = remember { mutableStateOf(themePreferenceManager.getThemePreference()) }

            LaunchedEffect(Unit) {
                themePreferenceManager.themePreference.collect { darkMode ->
                    isDarkTheme.value = darkMode
                }
            }

            SkyCheckTheme(darkTheme = isDarkTheme.value) {
                val navController = rememberNavController()
                val viewModel: FavouriteViewModel = viewModel(factory = viewModelFactory)

                Navigation(
                    nav = navController,
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme,
                    themePreferenceManager = themePreferenceManager
                )
            }
        }
    }
}


