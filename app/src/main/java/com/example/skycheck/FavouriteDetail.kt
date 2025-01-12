package com.example.skycheck

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavouriteDetail(
    nav: NavController,
    viewModel: FavouriteViewModel,
    cityId: Int // City ID passed from navigation
) {
    val city by viewModel.getCityById(cityId).collectAsState(initial = null)
    val context = LocalContext.current

    city?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "City: ${it.cityName}",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Simulating weather info (replace this with actual weather API call)
            Text(text = "Temperature: 25°C")
            Text(text = "Humidity: 60%")
            Text(text = "Weather: Sunny")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "${it.cityName} weather refreshed!", Toast.LENGTH_SHORT).show()
                    // Add weather refresh logic if necessary
                }
            ) {
                Text("Refresh Weather")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { nav.popBackStack() } // Navigate back to the previous screen
            ) {
                Text("Back to Favorites")
            }
        }
    } ?: run {
        Text(text = "Loading city details...", modifier = Modifier.padding(16.dp))
    }
}
