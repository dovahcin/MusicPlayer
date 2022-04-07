package com.example.musicplayer.features.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel(): ViewModel() {

    var _uiState = MutableStateFlow<PlayListUiState>(PlayListUiState.Success())
    val uiState: StateFlow<PlayListUiState> = _uiState

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = PlayListUiState.Failure(throwable)
    }

}