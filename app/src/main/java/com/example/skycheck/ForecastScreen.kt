package com.example.skycheck

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ForecastScreen(city: String, isDarkTheme: MutableState<Boolean>) {
    var forecastData by remember { mutableStateOf<ForecastResponse?>(null) }
    val apiKey = BuildConfig.API_KEY;
    val weatherApi = RetrofitInstance.api

    LaunchedEffect(city) {
        weatherApi.getForecast(city, apiKey).enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                if (response.isSuccessful) {
                    forecastData = response.body()
                } else {
                    Log.e("ForecastScreen", "Błąd podczas pobierania prognozy: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e("ForecastScreen", "Błąd podczas pobierania danych prognozy", t)
            }
        })
    }

    val pointsData: List<Point> = forecastData?.list?.mapIndexed { index, forecastItem ->
        Point(
            x = index.toFloat(),
            y = forecastItem.main.temp.toFloat()
        )
    } ?: listOf()

    val xAxisData = AxisData.Builder()
        .steps(pointsData.size - 1)
        .axisStepSize(100.dp)
        .labelData { index ->
            forecastData?.list?.getOrNull(index)?.dt_txt?.let { dtTxt ->
                val parts = dtTxt.split(" ")
                val dateTimeString = parts[0] + " " + parts[1]
                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val date = inputFormat.parse(dateTimeString)
                val outputFormat = SimpleDateFormat("dd.MM - HH:mm", Locale.getDefault())
                val formattedDate = date?.let { outputFormat.format(it) } ?: ""
                formattedDate
            } ?: ""
        }
        .labelAndAxisLinePadding(15.dp)
        .backgroundColor(Color.White)
        .build()



    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelData { step ->
            val minTemp = pointsData.minOfOrNull { it.y } ?: 0f
            val maxTemp = pointsData.maxOfOrNull { it.y } ?: 100f
            val range = maxTemp - minTemp
            val value = minTemp + (step * range / 5)
            "${value.formatToSinglePrecision()}°C"
        }
        .labelAndAxisLinePadding(20.dp)
        .backgroundColor(Color.White)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        forecastData?.let { forecast ->
            Text(
                text = "Prognoza dla miasta: ${forecast.city.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.CenterHorizontally),
                lineChartData = lineChartData
            )
        } ?: run {
            Text(text = "Ładowanie danych prognozy...", style = MaterialTheme.typography.bodyLarge)
        }
    }

}

fun Float.formatToSinglePrecision(): String = String.format("%.1f", this)
