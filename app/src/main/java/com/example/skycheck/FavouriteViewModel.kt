package com.example.skycheck

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavouriteViewModel(private val repo: FavouriteRepo): ViewModel()
{
    val allCities: Flow<List<Favourite>> = repo.allCities
    private val _isCityFavourite = MutableLiveData<Boolean>()
    val isCityFavourite: LiveData<Boolean> get() = _isCityFavourite

    private val _darkThemePreference = MutableStateFlow(false)
    val darkThemePreference: StateFlow<Boolean> = _darkThemePreference

    fun getCityById(cityId: Int): Flow<Favourite?> {
        return repo.getCityById(cityId)
    }

    fun checkIfCityIsFavourite(cityName: String) {
        viewModelScope.launch {
            val cityExists = repo.isCityFavourite(cityName)
            _isCityFavourite.value = cityExists
        }
    }

    suspend fun isCityFavourite(cityName: String): Boolean {
        return repo.isCityFavourite(cityName) != null
    }

    suspend fun getCityByName(cityName: String): Favourite? = repo.getCityByName(cityName)


    fun insert(fav: Favourite) = viewModelScope.launch{
        repo.insert(fav)
    }

    fun update(fav: Favourite) = viewModelScope.launch{
        repo.update(fav)
    }

    fun delete(fav: Favourite) = viewModelScope.launch{
        repo.delete(fav)
    }


    fun loadDarkThemePreference(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        _darkThemePreference.value = sharedPreferences.getBoolean("darkTheme", false)
    }

    fun saveDarkThemePreference(context: Context, newValue: Boolean) {
        val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("darkTheme", newValue)
        editor.apply()
        _darkThemePreference.value = newValue
    }


}