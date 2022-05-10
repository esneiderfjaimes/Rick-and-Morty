package com.red.rickandmorty.view.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.red.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.red.rickandmorty.view.adapter.EpisodesAdapter
import com.red.rickandmorty.view.util.navigateBack

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailsFragmentArgs by navArgs()

    private lateinit var episodesAdapter: EpisodesAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View {
        _binding = FragmentCharacterDetailsBinding.inflate(i, c, false).apply {
            val item = args.character

            image.load(item.image) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            status.text = item.status
            when (item.status) {
                "Alive" -> status.setTextColor(Color.GREEN)
                "Dead" -> status.setTextColor(Color.RED)
            }

            name.text = item.name
            gender.text = item.gender
            race.text = item.species
            origin.text = item.origin.name
            location.text = item.location.name

            // create  layoutManager
            val layoutManager =
                object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically(): Boolean = false
                }
            episodesRv.layoutManager = layoutManager

            episodesAdapter = EpisodesAdapter(item.episodes.toList())
            episodesRv.adapter = episodesAdapter

            appBarLayout.setNavigationOnClickListener { navigateBack() }
        }
        return binding.root
    }
}

