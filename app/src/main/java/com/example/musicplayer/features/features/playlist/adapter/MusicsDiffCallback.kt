package com.example.musicplayer.features.features.playlist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayer.features.domain.Music

class MusicsDiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean =
        areItemsTheSame(oldItem, newItem)
}