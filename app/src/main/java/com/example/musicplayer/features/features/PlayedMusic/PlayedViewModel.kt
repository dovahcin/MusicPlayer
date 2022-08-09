package com.example.musicplayer.features.features.playedmusic

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

    private val interval = 1000L

    fun getContentUri(appendedId: Long): Uri =
        playedRepository.getUriId(appendedId)

    fun getConnection() = playedRepository.getConnection()

    fun watchPlayBack() {
        viewModelScope.launch {
            delay(2000L)
            playedRepository.getPlayBackTime().collect { time ->
                while (isMusicPlaying(time)) {
                    playedRepository.getPlayBackTime().collect {
                        _time.value = time
                    }
                    delay(interval)
                }
            }
        }
    }

    private fun isMusicPlaying(time: Time) = time.currentPosition!! != time.duration!!
}