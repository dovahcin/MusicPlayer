package com.example.musicplayer.features.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlayedRepository {
    fun getMusicById(appendedId: Long): Uri = ContentUris.withAppendedId(
        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        appendedId
    )

}