package com.example.skycheck

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit API Interface
interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<WeatherResponse>
}