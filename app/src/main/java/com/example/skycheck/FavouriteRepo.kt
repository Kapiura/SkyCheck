package com.example.skycheck

import kotlinx.coroutines.flow.Flow

class FavouriteRepo(private val favDao: FavDao) {
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

    suspend fun insert(fav: Favourite) {
        val cleanCityName = normalizeCityName(fav.cityName)
        favDao.insertGrade(fav.copy(cityName = cleanCityName))
    }

    suspend fun update(fav: Favourite) {
        val cleanCityName = normalizeCityName(fav.cityName)
        favDao.updateGrade(fav.copy(cityName = cleanCityName))
    }

    suspend fun delete(fav: Favourite) {
        favDao.deleteGrade(fav)
    }
}
