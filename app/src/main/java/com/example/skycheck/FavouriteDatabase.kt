package com.example.skycheck

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 1)
abstract class FavouriteDatabase: RoomDatabase()
{
    abstract fun favDao(): FavDao

    companion object
    {
        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase
        {
            return INSTANCE?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}