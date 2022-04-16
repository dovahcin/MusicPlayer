package com.example.musicplayer.features.features.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlayListBinding
import com.example.musicplayer.features.domain.Music
import com.example.musicplayer.features.features.playlist.adapter.PlayListAdapter
import com.example.musicplayer.features.util.PlayListUiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListFragment : Fragment() {
    private var _binding: FragmentPlayListBinding? = null
    private val binding get() = _binding!!

    private val playListAdapter = PlayListAdapter()

    private val playListViewModel: PlayListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_list, container, false)

        playListViewModel.fetchListForView()

        binding.recyclerView.adapter = playListAdapter

        lifecycleScope.launch {
            playListViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is PlayListUiState.Success -> showMusics(uiState.musics)
                    is PlayListUiState.Failure -> showError(uiState.exception)
                }
                showLoadingView(uiState is PlayListUiState.Loading)
            }
        }

        return binding.root
    }

    private fun showMusics(musics: List<Music>) {
        if (musics.isNullOrEmpty()) {
            binding.textView.isVisible = true
            binding.recyclerView.isVisible = false
        } else {
            playListAdapter.update(musics)
        }
    }

    private fun showLoadingView(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    private fun showError(exception: Throwable) {
        Toast.makeText(requireContext(), "${exception.localizedMessage}!!", Toast.LENGTH_LONG)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}