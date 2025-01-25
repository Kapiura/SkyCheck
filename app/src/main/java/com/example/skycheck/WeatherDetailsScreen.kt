package com.example.skycheck

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import retrofit2.Response

@Composable
fun WeatherDetailsScreen(nav: NavController, city: String, viewModel: FavouriteViewModel) {
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }

    // Obserwowanie stanu, czy miasto jest w ulubionych
    val isCityFavourite by viewModel.isCityFavourite.observeAsState(false)
    var isLoading by remember { mutableStateOf(false) }

    val apiKey = BuildConfig.API_KEY
    val weatherApi = RetrofitInstance.api

    // Zapytanie o dane pogodowe
    LaunchedEffect(city) {
        weatherApi.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weatherData = response.body()
                    // Sprawdzamy, czy miasto jest w ulubionych
                    viewModel.checkIfCityIsFavourite(city)
                } else {
                    Log.e("WeatherDetails", "Error fetching weather: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherDetails", "Error fetching weather data", t)
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData?.let {
            Text(
                text = "Miasto: ${it.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Temperatura: ${it.main.temp}°C",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Min Temp: ${it.main.temp_min}°C",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Max Temp: ${it.main.temp_max}°C",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Opis: ${it.weather.firstOrNull()?.description}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Wilgotność: ${it.main.humidity}%",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Wiatr: ${it.wind?.speed} m/s",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (!isCityFavourite) {
                Button(
                    onClick = {
                        isLoading = true  // Turn on loading
                        viewModel.insert(Favourite(cityName = weatherData?.name ?: ""))  // Add city to favorites
                        viewModel.checkIfCityIsFavourite(weatherData?.name ?: "")  // Refresh the state to check if the city is in favorites
                        isLoading = false  // Turn off loading
                        // Navigate to the same screen with the city name to refresh it
                        nav.navigate("weatherDetailsScreen/${weatherData?.name}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text("Dodaj do ulubionych", fontSize = 16.sp)
                    }
                }

            }

            Button(
                onClick = { nav.navigate("forecast/${it.name}") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zobacz prognozę", fontSize = 16.sp)
            }
        } ?: run {
            Text(
                text = "Ładowanie danych...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
