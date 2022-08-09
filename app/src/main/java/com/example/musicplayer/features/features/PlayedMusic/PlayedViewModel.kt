package com.example.musicplayer.features.features.playedmusic

import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.domain.Time
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayedViewModel(private val playedRepository: PlayedRepository) : ViewModel() {
    private val TAG = "ViewModel"

    private var _time: MutableStateFlow<Time> = MutableStateFlow(Time())
    val time: StateFlow<Time> = _time

    private val handler = Handler()
    private lateinit var runnable: Runnable
    private val interval = 1000L

    fun getContentUri(appendedId: Long): Uri =
    playedRepository.getUriId(appendedId)

    fun initializeWatcher() {
        runnable = object : Runnable {
            override fun run() {
                getMusicCurrentPosition()
                handler.postDelayed(this, interval)
            }
        }
        handler.postDelayed(runnable, interval)
    }

    fun seekToChangedInterval(interval: Int) {
        playedRepository.playService?.seekTo(interval)
        Log.d(TAG, "$interval")
    }

    fun isPlayerPaused() = playedRepository.playService?.isPaused()

    fun pauseThePlayer() {
        playedRepository.playService?.pausePlayer()
    }

    fun playTheMusic() {
        playedRepository.playService?.playSong()
    }

    fun getMusicCurrentPosition() {
        viewModelScope.launch {
            playedRepository.getPlayer().collect {
                _time.value = it
            }
        }
    }

    fun getConnection() = playedRepository.connection

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(runnable)
    }
}