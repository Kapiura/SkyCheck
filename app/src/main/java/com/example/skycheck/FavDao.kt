package com.example.skycheck

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Query("SELECT * FROM favourites")
    fun getAllCities(): Flow<List<Favourite>>

    @Query("SELECT * FROM favourites WHERE id = :cityId")
    fun getCityById(cityId: Int): Flow<Favourite?>

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE cityName = :cityName LIMIT 1)")
    suspend fun isCityFavourite(cityName: String): Boolean  // Zwraca true, jeśli miasto jest w ulubionych

    @Query("SELECT * FROM favourites WHERE cityName = :cityName LIMIT 1")
    suspend fun getCityByName(cityName: String): Favourite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrade(fav: Favourite)

    @Update
    suspend fun updateGrade(fav: Favourite)

    @Delete
    suspend fun deleteGrade(fav: Favourite)
}
