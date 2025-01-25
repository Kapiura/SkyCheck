package com.example.skycheck

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavouritesScreen(nav: NavController, viewModel: FavouriteViewModel) {
    val cities by viewModel.allCities.collectAsState(initial = emptyList())

    if (cities.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(cities) { city ->
                // Item container with background and padding
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(color = Color.hsl(17F, 0.25F, 0.61F), shape = MaterialTheme.shapes.medium)
                        .clickable { nav.navigate("detailFav/${city.id}") }
                        .padding(16.dp) // Inside padding for content
                ) {
                    // Row for city name and button with spaced arrangement
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // City name text
                        Text(
                            text = city.cityName,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )

                        // Delete button with the background color correctly set
                        Button(
                            onClick = { viewModel.delete(city) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.hsl(6F, 0.42F, 0.42F)) // containerColor for the background
                        ) {
                            Text("Usuń", color = Color.White)
                        }

                    }
                }
            }
        }
    } else {
        // Message when no cities are added to favourites
        Text(
            text = "Brak miast dodanych do ulubionych",
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.padding(16.dp)
        )
    }
}
