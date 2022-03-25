package com.example.musicplayer.features.features.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlayListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListFragment : Fragment() {
    private var _binding: FragmentPlayListBinding? = null
    private val binding get() = _binding!!

    private val playListViewModel: PlayListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_list, container, false)

        playListViewModel.fetchListForView(requireContext())

        lifecycleScope.launch {
            playListViewModel.uiState.collect { uiState ->
                when(uiState) {
                    is PlayListUiState.Success -> showMusics()
                    is PlayListUiState.Failure -> showError()
                }
                showLoadingView(uiState is PlayListUiState.Loading)
            }
        }

        return binding.root
    }

    private fun showMusics() {
    }

    private fun showLoadingView(b: Boolean) {
    }

    private fun showError() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}