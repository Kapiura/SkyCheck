package com.example.skycheck

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun FavouriteDetail(
    nav: NavController,
    viewModel: FavouriteViewModel,
    cityId: Int
) {
    val city by viewModel.getCityById(cityId).collectAsState(initial = null)
    val context = LocalContext.current
    var weatherInfo by remember { mutableStateOf("Ładowanie danych...") }
    val apiKey = BuildConfig.API_KEY

    city?.let {
        LaunchedEffect(it.cityName) {
            getWeatherInfo(
                cityName = it.cityName,
                apiKey = apiKey,
                onSuccess = { data -> weatherInfo = data },
                onFailure = { error -> weatherInfo = error }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Szczegóły dla miasta: ${it.cityName}",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = weatherInfo,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    Toast.makeText(context, "Pogoda odświeżona!", Toast.LENGTH_SHORT).show()
                    getWeatherInfo(
                        cityName = it.cityName,
                        apiKey = apiKey,
                        onSuccess = { data -> weatherInfo = data },
                        onFailure = { error -> weatherInfo = error }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Odśwież", fontSize = 16.sp)
            }

            Button(
                onClick = { nav.navigate("forecast/${it.cityName}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Zobacz prognozę", fontSize = 16.sp)
            }

            Button(
                onClick = { nav.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Wróć do ulubionych", fontSize = 16.sp)
            }
        }
    } ?: run {
        Text(
            text = "Ładowanie danych...",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun getWeatherInfo(
    cityName: String,
    apiKey: String,
    onSuccess: (String) -> Unit,
    onFailure: (String) -> Unit
) {
    RetrofitInstance.api.getWeather(cityName, apiKey).enqueue(object : Callback<WeatherResponse> {
        override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
            if (response.isSuccessful) {
                val weather = response.body()
                if (weather != null) {
                    val weatherInfo = """
                        Temperatura: ${weather.main.temp}°C
                        Minimalna temperatura: ${weather.main.temp_min}°C
                        Maksymalna temperatura: ${weather.main.temp_max}°C
                        Wilgotność: ${weather.main.humidity}%
                        Ciśnienie: ${weather.main.pressure} hPa
                        Wiatr: ${weather.wind?.speed ?: "Brak danych"} m/s
                        Kierunek wiatru: ${weather.wind?.deg ?: "Brak danych"}°
                        Zachmurzenie: ${weather.clouds?.all ?: "Brak danych"}%
                        
                        Opis pogodowy:
                    """.trimIndent()

                    val weatherDescriptions = weather.weather.joinToString("\n") { desc ->
                        "- ${desc.description.capitalize()}"
                    }

                    onSuccess("$weatherInfo\n$weatherDescriptions")
                } else {
                    onFailure("Brak danych")
                }
            } else {
                onFailure("Błąd: ${response.code()} - ${response.message()}")
            }
        }

        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            onFailure("Błąd: ${t.localizedMessage}")
        }
    })
}
