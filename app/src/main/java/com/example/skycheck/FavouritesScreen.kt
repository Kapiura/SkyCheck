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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavouritesScreen(nav: NavController, viewModel: FavouriteViewModel) {
    val cities by viewModel.allCities.collectAsState(initial = emptyList())

    if(!cities.isEmpty())
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(cities) { city ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = Color.hsl(59F, 0.21F, 0.53F))
                        .clickable { nav.navigate("detailFav/${city.id}") }
                        .padding(30.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = city.cityName,
                            fontSize = 20.sp
                        )
                        Button(
                            onClick = {
                                viewModel.delete(city)
                            }
                        ) {
                            Text("Usuń")
                        }
                    }
                }
            }
        }
    }
    else
    {
        Text("Brak miast dodanych do ulubionych")
    }
}

