package com.example.musicplayer.features.features.playlist

import android.database.Cursor
import com.example.musicplayer.features.domain.Music

sealed class PlayListUiState {
    data class Success(val musics: MutableList<Music> = mutableListOf()): PlayListUiState()
    data class Failure(val exception: Throwable): PlayListUiState()
    object Loading: PlayListUiState()
}
