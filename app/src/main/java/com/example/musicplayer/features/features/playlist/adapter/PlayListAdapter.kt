package com.example.musicplayer.features.features.playlist.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.features.domain.Music

class PlayListAdapter(
    private val musicClick: (Long) -> Unit,
    private val items: MutableList<Music> = mutableListOf()
) : ListAdapter<Music, PlayListHolder>(MusicsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListHolder =
        PlayListHolder.create(parent)


    override fun onBindViewHolder(holder: PlayListHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            musicClick.invoke(items[position].id)
        }
    }

    override fun getItemCount(): Int = items.count()

    fun update(musics: List<Music>) {
        items.addAll(musics)
        notifyItemRangeInserted(itemCount, musics.size)
    }
}