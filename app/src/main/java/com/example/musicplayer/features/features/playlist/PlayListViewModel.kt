package com.example.musicplayer.features.features.playlist

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.features.data.PlayListRepository
import com.example.musicplayer.features.domain.Music
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayListViewModel(private val playedRepository: PlayListRepository) : ViewModel() {
    private var _uiState = MutableStateFlow<PlayListUiState>(PlayListUiState.Success())
    val uiState: StateFlow<PlayListUiState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = PlayListUiState.Failure(throwable)
    }

    fun fetchListForView(context: Context) {
        viewModelScope.launch(exceptionHandler) {
            playedRepository.fetchMusics(context)
                .onStart { _uiState.value = PlayListUiState.Loading }
                .collect { cursor ->
                    _uiState.value = PlayListUiState.Success(cursor)
                }
        }
    }
}