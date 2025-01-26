package com.example.skycheck

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemePreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    private val _themePreference = MutableStateFlow(getThemePreference())
    val themePreference: Flow<Boolean> get() = _themePreference.asStateFlow()

    fun saveThemePreference(isDarkTheme: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode_state", isDarkTheme).apply()
        _themePreference.value = isDarkTheme
    }

    fun getThemePreference(): Boolean {
        return sharedPreferences.getBoolean("dark_mode_state", false)
    }
}