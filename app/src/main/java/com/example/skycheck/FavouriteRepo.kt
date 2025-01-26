package com.example.skycheck

import kotlinx.coroutines.flow.Flow

class FavouriteRepo(private val favDao: FavDao)
{
    val allCities: Flow<List<Favourite>> = favDao.getAllCities()

    fun getCityById(cityId: Int): Flow<Favourite?> {
        return favDao.getCityById(cityId)
    }

    suspend fun getCityByName(cityName: String): Favourite? {
        return favDao.getCityByName(cityName)
    }

    suspend fun isCityFavourite(cityName: String): Boolean {
        return favDao.getCityByName(cityName) != null
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