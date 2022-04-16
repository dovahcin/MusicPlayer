package com.example.musicplayer.features.util

import com.example.musicplayer.features.domain.Music

sealed class PlayListUiState : BaseState() {
    data class Success(val musics: List<Music> = mutableListOf()): PlayListUiState()
    data class Failure(val exception: Throwable): PlayListUiState()
    object Loading: PlayListUiState()
}


