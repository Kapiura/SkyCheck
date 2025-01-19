package com.example.skycheck

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WeatherDetailsScreen(city: String, viewModel: FavouriteViewModel) {
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    val apiKey = "7b7fe4dd87c83d143654327eaa81fdd8"
    val weatherApi = RetrofitInstance.api

    // Fetch the weather data for the city passed in the route
    LaunchedEffect(city) {
        weatherApi.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weatherData = response.body()
                } else {
                    Log.e("WeatherDetails", "Error fetching weather: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherDetails", "Error fetching weather data", t)
            }
        })
    }

    // Display weather details
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData?.let {
            Text(text = "Miasto: ${it.name}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Temperatura: ${it.main.temp}°C", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Min Temp: ${it.main.temp_min}°C", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Max Temp: ${it.main.temp_max}°C", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Opis: ${it.weather.firstOrNull()?.description}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Wilgotność: ${it.main.humidity}%", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Wiatr: ${it.wind?.speed} m/s", style = MaterialTheme.typography.bodyLarge)
            Button(
                onClick = {
                    viewModel.insert(Favourite(cityName = it.name))
                }
            ) {
                Text("Dodaj do ulubionych")
            }
        } ?: run {
            Text(text = "Loading weather data...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

