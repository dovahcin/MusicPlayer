package com.example.musicplayer.features.features.playedmusic

import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.domain.Time
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayedViewModel(private val playedRepository: PlayedRepository) : ViewModel() {
    private var _time: MutableStateFlow<Time> = MutableStateFlow(Time())
    val time: StateFlow<Time> = _time

    private var _player: MutableStateFlow<MediaPlayer> = MutableStateFlow(MediaPlayer())
    val player: StateFlow<MediaPlayer> = _player

    private val interval = 1000L

    fun getContentUri(appendedId: Long): Uri =
        playedRepository.getUriId(appendedId)

    fun getConnection() = playedRepository.getConnection()

    fun watchPlayBack() {
        viewModelScope.launch {
            delay(2000L)
            playedRepository.getPlayer().collect { player ->
                while (isMusicPlaying(player)) {
                    playedRepository.getPlayer().collect {
                        _time.value = Time(player.duration, player.currentPosition)
                    }
                    delay(interval)
                }
            }
        }
    }

    fun getPlayer() {
        viewModelScope.launch {
            playedRepository.getPlayer().collect { player ->
                _player.value = player
            }
        }
    }

    private fun isMusicPlaying(player: MediaPlayer) = player.currentPosition != player.duration
}