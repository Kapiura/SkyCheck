package com.example.skycheck

import kotlinx.coroutines.flow.Flow

class FavouriteRepo(private val favDao: FavDao)
{
    val allGrades: Flow<List<Favourite>> = favDao.getAllCities()

    fun getCityById(cityId: Int): Flow<Favourite?> {
        return favDao.getCityById(cityId)
    }

    suspend fun insert(fav: Favourite)
    {
        favDao.insertGrade(fav)
    }

    suspend fun update(fav: Favourite)
    {
        favDao.updateGrade(fav)
    }

    suspend fun delete(fav: Favourite)
    {
        favDao.deleteGrade(fav)
    }
}