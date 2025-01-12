package com.example.skycheck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavouriteViewModelFactory(private val repo: FavouriteRepo): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavouriteViewModel::class.java))
        {
            @Suppress("niesprawdzony czek")
            return FavouriteViewModel(repo) as T
        }
        throw IllegalArgumentException("nienznay viewmodel class")
    }
}