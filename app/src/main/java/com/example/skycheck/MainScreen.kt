package com.example.skycheck

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    nav: NavController,
    viewModel: FavouriteViewModel,
    isDarkTheme: MutableState<Boolean>
) {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE) }

    val savedCity by remember {
        mutableStateOf(sharedPreferences.getString("saved_text", "Unknown City") ?: "Unknown City")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Witaj!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Zapisane miasto: $savedCity",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                nav.navigate("weatherDetailsScreen/$savedCity")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pokaż szczegóły pogody")
        }
    }
}

