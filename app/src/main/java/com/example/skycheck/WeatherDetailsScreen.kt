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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import retrofit2.Response

@Composable
fun WeatherDetailsScreen(nav: NavController, city: String, viewModel: FavouriteViewModel) {
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }

    val isCityFavourite by viewModel.isCityFavourite.observeAsState(false)
    var isLoading by remember { mutableStateOf(false) }

    val apiKey = BuildConfig.API_KEY
    val weatherApi = RetrofitInstance.api

    LaunchedEffect(city) {
        weatherApi.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weatherData = response.body()
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherData?.let { data ->
            Text(
                text = "Szczegóły pogodowe dla miasta: ${data.name}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text("Temperatura: ${data.main.temp}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Minimalna temperatura: ${data.main.temp_min}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Maksymalna temperatura: ${data.main.temp_max}°C", style = MaterialTheme.typography.bodyLarge)
            Text("Wilgotność: ${data.main.humidity}%", style = MaterialTheme.typography.bodyLarge)
            Text("Ciśnienie: ${data.main.pressure} hPa", style = MaterialTheme.typography.bodyLarge)
            Text("Prędkość wiatru: ${data.wind?.speed ?: "Brak danych"} m/s", style = MaterialTheme.typography.bodyLarge)
            Text("Kierunek wiatru: ${data.wind?.deg ?: "Brak danych"}°", style = MaterialTheme.typography.bodyLarge)
            Text("Zachmurzenie: ${data.clouds?.all ?: "Brak danych"}%", style = MaterialTheme.typography.bodyLarge)

            data.weather.forEachIndexed { index, weather ->
                Text(
                    text = "Opis pogody (${index + 1}): ${weather.description}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isCityFavourite) {
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.insert(Favourite(cityName = data.name))
                        viewModel.checkIfCityIsFavourite(data.name)
                        isLoading = false
                    },
                    modifier = Modifier.fillMaxWidth()
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
                onClick = { nav.navigate("forecast/${data.name}") },
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
