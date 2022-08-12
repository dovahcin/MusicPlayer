package com.example.musicplayer.features.features.playedmusic

sealed class PlayState {
    object IsPlaying: PlayState()
    object IsStopped: PlayState()
    object IsPaused: PlayState()
}
