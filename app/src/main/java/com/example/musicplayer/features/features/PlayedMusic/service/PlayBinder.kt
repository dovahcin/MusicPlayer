package com.example.musicplayer.features.features.playedmusic.service

import android.os.Binder

class PlayBinder : Binder() {
    fun getService(): PlayService = PlayService()
}