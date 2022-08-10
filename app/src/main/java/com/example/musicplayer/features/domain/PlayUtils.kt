package com.example.musicplayer.features.domain

import android.media.MediaPlayer
import com.example.musicplayer.features.features.playedmusic.PlayState

data class PlayUtils(
    val player: MediaPlayer = MediaPlayer(),
    val playState: PlayState = PlayState.IsStopped
)




