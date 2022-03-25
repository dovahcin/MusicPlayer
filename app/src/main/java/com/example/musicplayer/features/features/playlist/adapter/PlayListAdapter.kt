package com.example.musicplayer.features.features.playlist.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.features.domain.Music

class PlayListAdapter(private val items: MutableList<Music> = mutableListOf()) : RecyclerView.Adapter<PlayListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListHolder =
        PlayListHolder.create(parent)


    override fun onBindViewHolder(holder: PlayListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun update(musics: MutableList<Music>) {
        items.addAll(musics)
        notifyItemRangeInserted(itemCount, musics.size)
    }
}