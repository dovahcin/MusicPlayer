package com.example.musicplayer.features.features.playlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemMusicListBinding
import com.example.musicplayer.features.domain.Music

class PlayListHolder(private val binding: ItemMusicListBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) =
            PlayListHolder(
                ItemMusicListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    fun bind(music: Music) {
        binding.music = music
        binding.executePendingBindings()
    }
}