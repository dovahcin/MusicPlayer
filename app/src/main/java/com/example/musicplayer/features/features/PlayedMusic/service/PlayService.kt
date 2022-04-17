package com.example.musicplayer.features.features.playedmusic.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.musicplayer.features.data.PlayedRepository.Companion.PLAY_EXTRA

class PlayService : Service(), MediaPlayer.OnPreparedListener {
    private var mediaPlayer: MediaPlayer? = null

    private val TAG = "Service"

    override fun onBind(p0: Intent?): IBinder = PlayBinder()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnPreparedListener(this)
        Log.d(TAG, "onCreate : ${mediaPlayer?.duration}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStartCommand : ")
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )

        mediaPlayer?.setDataSource(this, intent?.getParcelableExtra(PLAY_EXTRA)!!)
        mediaPlayer?.prepareAsync()

        return START_STICKY
    }

    fun getPlayer(): MediaPlayer {
        Log.d(TAG, "getPlayer : ${mediaPlayer?.duration}")
        return mediaPlayer!!
    }

    override fun onPrepared(p0: MediaPlayer?) {
        mediaPlayer?.start()
        Log.d(TAG, "onPrepared : ${mediaPlayer?.duration}")
    }
}