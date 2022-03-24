package com.example.musicplayer.features.di

import androidx.room.Room
import com.example.musicplayer.features.data.db.PlayerDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PlayerDatabase::class.java,
            "music_player_database"
        ).build()
            .favorites()
    }
}