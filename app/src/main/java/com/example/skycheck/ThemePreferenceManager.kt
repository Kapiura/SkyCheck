package com.example.skycheck

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException

// Definicja dataStore za pomocą preferencesDataStore
val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferenceManager(private val context: Context) {

    private object PreferencesKeys {
        val THEME_MODE = booleanPreferencesKey("theme_mode")
    }

    // Pobranie danych motywu z dataStore
    val themePreference: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // Jeśli błąd, ustaw domyślną wartość
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.THEME_MODE] ?: false // Domyślnie motyw jasny
        }

    // Zapisanie preferencji motywu do dataStore
    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = isDarkTheme
        }
    }
}
