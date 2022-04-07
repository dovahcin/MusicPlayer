package com.example.musicplayer.features.features.playlist

import androidx.lifecycle.viewModelScope
import com.example.musicplayer.features.data.PlayListRepository
import com.example.musicplayer.features.util.BaseViewModel
import com.example.musicplayer.features.util.PlayListUiState
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayListViewModel(private val playedRepository: PlayListRepository) : BaseViewModel() {

    fun fetchListForView() {
        viewModelScope.launch(exceptionHandler) {
            playedRepository.fetchMusics()
                .onStart { _uiState.value = PlayListUiState.Loading }
                .collect { cursor ->
                    _uiState.value = PlayListUiState.Success(cursor)
                }
        }
    }
}