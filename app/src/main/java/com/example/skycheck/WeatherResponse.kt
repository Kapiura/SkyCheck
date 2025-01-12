package com.example.skycheck

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String,
    val wind: Wind?,
    val clouds: Clouds?,
    val snow: Snow?
)