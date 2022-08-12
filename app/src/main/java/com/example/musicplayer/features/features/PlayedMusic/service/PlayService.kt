package com.example.musicplayer.features.features.playedmusic.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.features.playedmusic.PlayState

class PlayService : Service(), MediaPlayer.OnPreparedListener {
    private var mediaPlayer: MediaPlayer? = null

    private val TAG = "Service"

    override fun onBind(p0: Intent?): IBinder = PlayBinder()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnPreparedListener(this)
    }

    inner class PlayBinder : Binder() {
        fun getService(): PlayService = this@PlayService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initializePlayer()
        preparePlayer(intent)
        return START_STICKY
    }

    private fun initializePlayer() {
        mediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    private fun preparePlayer(intent: Intent?) {
        mediaPlayer?.setDataSource(this, intent?.getParcelableExtra(PlayedRepository.PLAY_EXTRA)!!)
        mediaPlayer?.prepareAsync()
    }

    fun playNextSong(intent: Intent?) {
        mediaPlayer?.setDataSource(this, intent?.getParcelableExtra(PlayedRepository.PLAY_EXTRA)!!)
        mediaPlayer?.prepareAsync()
    }

    fun stopPlayer() {
        mediaPlayer?.stop()
    }

    fun releasePlayer() {
        mediaPlayer?.release()
    }

    fun getPlayer(): MediaPlayer {
        return mediaPlayer!!
    }

    fun getPlayState(): PlayState {
        return when {
            !mediaPlayer!!.isPlaying && mediaPlayer!!.currentPosition > 1 -> PlayState.IsPaused
            !mediaPlayer!!.isPlaying && mediaPlayer!!.currentPosition < 1 -> PlayState.IsStopped
            else -> PlayState.IsPlaying
        }
    }

    override fun onPrepared(player: MediaPlayer?) {
        player?.start()
    }
}