package com.example.skycheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repo: FavouriteRepo): ViewModel()
{
    val allCities: Flow<List<Favourite>> = repo.allGrades

    fun getCityById(cityId: Int): Flow<Favourite?> {
        return repo.getCityById(cityId)
    }

    fun insert(fav: Favourite) = viewModelScope.launch{
        repo.insert(fav)
    }

    fun update(fav: Favourite) = viewModelScope.launch{
        repo.update(fav)
    }

    fun delete(fav: Favourite) = viewModelScope.launch{
        repo.delete(fav)
    }
}