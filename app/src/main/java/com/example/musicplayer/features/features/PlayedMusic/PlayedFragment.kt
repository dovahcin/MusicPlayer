package com.example.musicplayer.features.features.playedmusic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlayedBinding
import com.example.musicplayer.features.data.PlayedRepository.Companion.PLAY_EXTRA
import com.example.musicplayer.features.features.playedmusic.service.PlayService
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayedFragment : Fragment() {
    private var _binding: FragmentPlayedBinding? = null
    private val binding get() = _binding!!

    private val playedViewModel: PlayedViewModel by viewModel()

    private val navArgs: PlayedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_played, container, false)

        val contentUri = playedViewModel.getContentUri(navArgs.appendedId)

        binding.buttonPlay.setOnClickListener {
            Intent(context, PlayService::class.java).also { intent ->
                intent.putExtra(PLAY_EXTRA, contentUri)
                context?.startService(intent)
            }
            playedViewModel.initializeWatcher()
        }

        binding.buttonForward.setOnClickListener {
            playedViewModel.initializeWatcher()
        }

        lifecycleScope.launch {
            playedViewModel.time.collect { time ->
                binding.seekBar.max = time.duration!!
                binding.seekBar.progress = time.currentPosition!!
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, currentPosition: Int, isChanged: Boolean) {
                if (isChanged) {

                }
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Intent(context, PlayService::class.java).also { intent ->
            context?.bindService(intent, playedViewModel.getConnection(), Context .BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        context?.unbindService(playedViewModel.getConnection())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}