package com.example.musicplayer.features.features.playedmusic

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.util.BaseViewModel
import com.example.musicplayer.features.util.PlayListUiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayedViewModel(private val playedRepository: PlayedRepository) : ViewModel() {
    fun getMusic(appendedId: Long): Uri =
    playedRepository.getMusicById(appendedId)
}