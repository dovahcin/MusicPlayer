package com.example.musicplayer.features.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicplayer.features.domain.Favorite

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favorite: Favorite)

    @Query("SELECT * FROM favorites_table ORDER BY songName ASC")
    suspend fun getAllFavorites(): MutableList<Favorite>

    @Query("DELETE FROM favorites_table WHERE songName = :songName")
    suspend fun deleteFavoriteSong(songName: String)

}
