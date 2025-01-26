package com.example.skycheck

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    nav: NavController,
    viewModel: FavouriteViewModel,
    isDarkTheme: MutableState<Boolean>,
    themePreferenceManager: ThemePreferenceManager
) {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE) }

    var savedText by remember { mutableStateOf(sharedPreferences.getString("saved_text", "") ?: "") }

    fun saveTextToPreferences(text: String) {
        sharedPreferences.edit().putString("saved_text", text).apply()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Ustawienia", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isDarkTheme.value,
                onCheckedChange = { checked ->
                    isDarkTheme.value = checked
                    themePreferenceManager.saveThemePreference(checked)
                }
            )
            Text(text = "Dark Mode", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = savedText,
            onValueChange = { newText ->
                savedText = newText
                saveTextToPreferences(newText)
            },
            label = { Text("Główne miasto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}



