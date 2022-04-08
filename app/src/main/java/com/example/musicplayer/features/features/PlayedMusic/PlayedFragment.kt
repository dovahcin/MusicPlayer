package com.example.musicplayer.features.features.playedmusic

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlayedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayedFragment : Fragment() {
    private var _binding: FragmentPlayedBinding? = null
    private val binding get() = _binding!!

    private lateinit var runnable: Runnable
    private var handler = Handler()

    var mediaPlayer: MediaPlayer? = MediaPlayer()

    private val playedViewModel: PlayedViewModel by viewModel()

    private val navArgs: PlayedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_played, container, false)

        val contentUri = playedViewModel.getMusic(navArgs.appendedId)


        binding.buttonPlay.setOnClickListener {
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer?.start()
                it.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            } else {
                mediaPlayer?.pause()
                it.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, currentPosition: Int, isChanged: Boolean) {
                if (isChanged)
                    mediaPlayer?.seekTo(currentPosition)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
            }
        })

        lifecycleScope.launch(Dispatchers.Default) {
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            mediaPlayer?.setDataSource(requireContext(), contentUri)
            mediaPlayer?.prepareAsync()

            runnable = Runnable {
                binding.seekBar.max = mediaPlayer!!.duration
                binding.seekBar.progress = mediaPlayer!!.currentPosition
                handler.postDelayed(runnable, 100)
            }
            handler.postDelayed(runnable, 100)

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}