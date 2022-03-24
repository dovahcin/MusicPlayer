package com.example.musicplayer.features.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    val songName: String,
    val artistName: String
)
