package com.example.musicplayer.features.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicplayer.features.domain.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class PlayerDatabase : RoomDatabase(){
    abstract fun favorites(): FavoritesDao
}