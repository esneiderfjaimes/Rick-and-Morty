package com.red.rickandmorty.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.red.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.red.rickandmorty.view.adapter.EpisodesAdapter
import com.red.rickandmorty.view.parcelables.EpisodeParcelable
import com.red.rickandmorty.view.util.*
import com.red.rickandmorty.viewmodel.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by viewModels()

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailsFragmentArgs by navArgs()

    private lateinit var episodesAdapter: EpisodesAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View {
        val character = args.character
        _binding = FragmentCharacterDetailsBinding.inflate(i, c, false).apply {

            character.load(image, name, status, gender, race, origin, location)
            character.statusDeco(status)

            fabBack.setOnClickListener { navigateBack() }
            fabPrint.setOnClickListener {
                navigateTo(CharacterDetailsFragmentDirections.toPrint(character))
            }
        }
        setUpRecyclerView(character.episodes)
        return binding.root
    }

    private fun setUpRecyclerView(episodes: Map<Int, EpisodeParcelable?>) {
        // create  layoutManager
        val layoutManager =
            object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
        binding.episodesRv.layoutManager = layoutManager

        // define adapter
        episodesAdapter = EpisodesAdapter(episodes.toMutableList())
        binding.episodesRv.adapter = episodesAdapter

        // search for extra information about the episodes and update the recycler view
        viewModel.searchEpisodes(episodes.filter { it.value == null }.keysList())
        viewModel.episode.observe(viewLifecycleOwner) {
            episodesAdapter.updateEpisode(it)
        }
    }
}

