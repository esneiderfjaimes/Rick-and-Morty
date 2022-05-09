package com.red.rickandmorty.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.red.rickandmorty.R
import com.red.rickandmorty.databinding.FragmentCharacterListBinding
import com.red.rickandmorty.view.adapter.GridAdapter
import com.red.rickandmorty.view.parcelables.toParcelable
import com.red.rickandmorty.viewmodel.CharactersViewModel
import com.red.rickandmorty.viewmodel.CharactersViewModel.CharacterListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rickandmorty.domain.models.PageCharacters

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var gridAdapter: GridAdapter

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View {
        _binding = FragmentCharacterListBinding.inflate(i, c, false).apply {
            // Start a coroutine in the lifecycle scope
            lifecycleScope.launch {
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    viewModel.uiState.collect { uiState ->
                        changeUiState(uiState)
                    }
                }
            }
            gridAdapter = GridAdapter(requireContext()) {

            }
            charactersGrid.adapter = gridAdapter
            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.before -> {
                        viewModel.before()
                        true
                    }
                    R.id.next -> {
                        viewModel.next()
                        true
                    }
                    else -> false
                }
            }
        }
        return binding.root
    }

    private fun changeUiState(uiState: CharacterListUiState) {
        when (uiState) {
            is CharacterListUiState.Error -> Unit
            is CharacterListUiState.Loading -> {
                binding.progressCircular.visibility =
                    if (uiState.isLoading) View.VISIBLE else View.GONE
            }
            is CharacterListUiState.Success -> updatePage(uiState.page)
        }
    }

    private fun updatePage(pageCharacters: PageCharacters) {
        // Update grid
        gridAdapter.updateItems(pageCharacters.results.map { it.toParcelable() })

        val info = pageCharacters.info

        // Update top app bar
        binding.topAppBar.apply {
            val pageInfoText =
                resources.getString(R.string.page) + " " + info.currentPage + "-" + info.pages
            (menu[0].actionView as TextView).text = pageInfoText
            menu[1].isEnabled = info.prev != null
            menu[2].isEnabled = info.next != null
        }
    }

}

