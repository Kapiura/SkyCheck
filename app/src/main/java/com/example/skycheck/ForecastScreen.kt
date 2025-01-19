package com.example.skycheck

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ForecastScreen(city: String) {
    var forecastData by remember { mutableStateOf<ForecastResponse?>(null) }
    val apiKey = "7b7fe4dd87c83d143654327eaa81fdd8"
    val weatherApi = RetrofitInstance.api

    // Fetch the forecast data for the city
    LaunchedEffect(city) {
        weatherApi.getForecast(city, apiKey).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    forecastData = response.body()
                } else {
                    Log.e("ForecastScreen", "Error fetching forecast: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("ForecastScreen", "Error fetching forecast data", t)
            }
        })
    }

    // Display forecast details
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        forecastData?.let { forecast ->
            Text(
                text = "Prognoza dla miasta: ${forecast.city.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(forecast.list) { forecastItem ->
                    ForecastItemView(forecastItem)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } ?: run {
            Text(text = "Loading forecast data...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ForecastItemView(forecastItem: ForecastItem) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Data: ${forecastItem.dt_txt}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Temperatura: ${forecastItem.main.temp}°C", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Opis: ${forecastItem.weather.firstOrNull()?.description}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Wiatr: ${forecastItem.wind?.speed} m/s", style = MaterialTheme.typography.bodyMedium)
    }
}
