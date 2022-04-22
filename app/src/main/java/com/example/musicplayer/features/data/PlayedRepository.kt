package com.example.musicplayer.features.data

import android.content.ComponentName
import android.content.ContentUris
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.example.musicplayer.features.domain.Time
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

    fun getUriId(appendedId: Long): Uri = ContentUris.withAppendedId(
        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        appendedId
    )

    fun getPlayer() = flow {
        if (isBounded) {
            emit(
                Time(
                    playService?.getPlayer()?.duration,
                    playService?.getPlayer()?.currentPosition,
                )
            )
            Log.d("Repository", "${playService?.getPlayer()?.currentPosition}")
        }
    }.flowOn(Dispatchers.IO)

    val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as PlayService.PlayBinder
            playService = binder.getService()
            isBounded = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isBounded = false
        }
    }

}