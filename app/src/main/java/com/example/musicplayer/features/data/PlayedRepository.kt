package com.example.musicplayer.features.data

import android.content.ComponentName
import android.content.ContentUris
import android.content.Context
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.example.musicplayer.features.features.playedmusic.service.PlayBinder
import com.example.musicplayer.features.features.playedmusic.service.PlayService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlayedRepository {
    private var isBounded = false
    private var playService: PlayService? = null

    companion object {
        const val PLAY_EXTRA = "Play_Extra"
    }

    fun getMusicById(appendedId: Long): Uri = ContentUris.withAppendedId(
        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        appendedId
    )

    fun initWatcher() {
        if (isBounded)
            Log.d("Repository", "${playService?.getPlayer()?.duration}")
    }

    val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as PlayBinder
            playService = binder.getService()
            isBounded = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isBounded = false
        }
    }

}