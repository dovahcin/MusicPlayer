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
import com.example.musicplayer.features.data.PlayedRepository
import com.example.musicplayer.features.features.playedmusic.service.PlayService
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayedFragment : Fragment(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private var _binding: FragmentPlayedBinding? = null
    private val binding get() = _binding!!

    private val playedViewModel: PlayedViewModel by viewModel()

    private val navArgs: PlayedFragmentArgs by navArgs()

    private val contentUri get() = playedViewModel.getContentUri(navArgs.appendedId)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_played, container, false)

        binding.buttonPlayback.setOnClickListener(this)
        binding.seekBar.setOnSeekBarChangeListener(this)

        lifecycleScope.launch {
            playedViewModel.time.collect { time ->
                binding.seekBar.max = time.duration!!
                binding.seekBar.progress = time.currentPosition!!
            }
        }

        Intent(context, PlayService::class.java).also { intent ->
            intent.putExtra(PlayedRepository.PLAY_EXTRA, contentUri)
            context?.startService(intent)
        }
        playedViewModel.initializeWatcher()
        binding.buttonPlayback.setBackgroundResource(R.drawable.ic_baseline_pause_24)

        return binding.root
    }

    override fun onClick(button: View) {
        if (!playedViewModel.isPlayerPaused()!!) {
            playedViewModel.pauseThePlayer()
            button.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)
        } else {
            playedViewModel.playTheMusic()
            button.setBackgroundResource(R.drawable.ic_baseline_pause_24)
        }

    }

    override fun onProgressChanged(seekbar: SeekBar?, currentPosition: Int, isChanged: Boolean) {
        if (isChanged) {
            playedViewModel.seekToChangedInterval(currentPosition)
        }
    }

    override fun onStartTrackingTouch(seekbar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekbar: SeekBar?) {
    }

    override fun onStart() {
        super.onStart()
        context?.bindService(
            Intent(context, PlayService::class.java),
            playedViewModel.getConnection(), Context.BIND_AUTO_CREATE
        )
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