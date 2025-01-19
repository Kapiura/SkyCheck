package com.example.skycheck

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "favourites",
)
data class Favourite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String
)