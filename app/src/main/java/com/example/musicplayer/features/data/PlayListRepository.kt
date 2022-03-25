package com.example.musicplayer.features.data

import android.content.Context
import com.example.musicplayer.features.domain.Music
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.IllegalArgumentException

class PlayListRepository {

    suspend fun fetchMusics(context: Context) = flow {
        val resolver = context.contentResolver
        val uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = resolver.query(
            uri, null, null, null, null
        )

        when (cursor) {
            null -> throw IllegalArgumentException("Invalid content uri.")
            else -> {
                val nameColumn =
                    cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE)
                val artistColumn =
                    cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)
                val idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(nameColumn)
                    val artist = cursor.getString(artistColumn)
                    emit(
                        mutableListOf(
                            Music(
                                id,
                                title,
                                artist
                            )
                        )
                    )
                }
                cursor.close()
            }
        }
    }.flowOn(Dispatchers.IO)
}