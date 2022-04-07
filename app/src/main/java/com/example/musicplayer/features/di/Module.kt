package com.example.musicplayer.features.di

import androidx.room.Room
import com.example.musicplayer.features.data.PlayListRepository
import com.example.musicplayer.features.data.db.PlayerDatabase
import com.example.musicplayer.features.features.playlist.PlayListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
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
val mainModule = module {
    viewModel { PlayListViewModel(get()) }
    single { PlayListRepository(get()) }
}