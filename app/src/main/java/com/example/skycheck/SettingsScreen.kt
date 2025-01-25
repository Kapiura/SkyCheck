package com.example.skycheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(nav: NavController, viewModel: FavouriteViewModel, isDarkTheme: MutableState<Boolean>) {
    // Main Column for vertical layout
    Column(modifier = Modifier.padding(16.dp)) {
        // Title at the top
        Text(text = "Ustawienia", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp)) // Spacer to give margin between title and other UI elements

        // Row for switch and label
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Switch for dark mode toggle
            Switch(
                checked = isDarkTheme.value,
                onCheckedChange = { isDarkTheme.value = it }
            )

            // Label next to the switch
            Text(text = "Dark Mode", modifier = Modifier.padding(start = 8.dp))
        }
    }
}
