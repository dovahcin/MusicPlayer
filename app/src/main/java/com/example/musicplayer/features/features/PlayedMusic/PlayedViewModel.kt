package com.example.musicplayer.features.features.playedmusic

import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.domain.PlayUtils
import com.example.musicplayer.features.domain.Time
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayedViewModel(private val playedRepository: PlayedRepository) : ViewModel() {
    private var _playUtils: MutableStateFlow<PlayUtils> =
        MutableStateFlow(PlayUtils())
    val playUtils: StateFlow<PlayUtils> = _playUtils

    private var _time: MutableStateFlow<Time> =
        MutableStateFlow(Time())
    val time: StateFlow<Time> = _time

    private val interval = 1000L

    fun getContentUri(appendedId: Long): Uri =
        playedRepository.getUriId(appendedId)

    fun getConnection() = playedRepository.getConnection()

    fun watchPlayBack() {
        viewModelScope.launch {
            delay(1000L)
            playedRepository.getPlayService().collect { service ->
                while (isMusicPlaying(service.getPlayer())) {
                    playedRepository.getPlayService().collect {
                        _time.value =
                            Time(service.getPlayer().duration, service.getPlayer().currentPosition)
                    }
                    delay(interval)
                }
            }
        }
    }

    fun getPlayer() {
        viewModelScope.launch {
            playedRepository.getPlayService().collect { service ->
                _playUtils.value = PlayUtils(
                    service.getPlayer(), service.getPlayState()
                )
            }
        }
    }

    private fun isMusicPlaying(player: MediaPlayer) = player.currentPosition != player.duration
}