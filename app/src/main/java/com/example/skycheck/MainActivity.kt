package com.example.skycheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.ui.theme.SkyCheckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repo = FavouriteRepo(FavouriteDatabase.getDatabase(this).favDao())
        val viewModelFactory = FavouriteViewModelFactory(repo)
        setContent {
            SkyCheckTheme {
                val navController = rememberNavController()
                val viewModel: FavouriteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory
                )
                Navigation(navController, viewModel)
            }
        }
    }
}

