// SearchScreen.kt
package com.example.skycheck

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SearchScreen(nav: NavController) {
    var city by remember { mutableStateOf("Warsaw") }
    var weatherInfo by remember { mutableStateOf("Wpisz nazwe miasta by wyszukać pogodę") }
    val apiKey = "7b7fe4dd87c83d143654327eaa81fdd8"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Miasto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            RetrofitInstance.api.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        val weather = response.body()
                        if (weather != null) {
                            weatherInfo = "Pomyślnie pobrano dane"
                            nav.navigate("weatherDetailsScreen/${city}")
                        } else {
                            weatherInfo = "Brak danych"
                        }
                    } else {
                        weatherInfo = "Błąd: ${response.code()} - ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("WeatherApp", "Błąd: ${t.localizedMessage}")
                    weatherInfo = "Błąd: ${t.localizedMessage}"
                }
            })
        }) {
            Text("Wyszukaj")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = weatherInfo, style = MaterialTheme.typography.bodyLarge)
    }
}
