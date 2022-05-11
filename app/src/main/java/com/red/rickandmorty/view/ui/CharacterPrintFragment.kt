package com.red.rickandmorty.view.ui

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.red.rickandmorty.R
import com.red.rickandmorty.databinding.FragmentCharacterPrintBinding
import com.red.rickandmorty.databinding.ItemPrintBinding
import com.red.rickandmorty.view.parcelables.CharacterParcelable
import com.red.rickandmorty.view.util.load
import com.red.rickandmorty.view.util.navigateBack
import com.red.rickandmorty.viewmodel.CharacterPrintViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterPrintFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCharacterPrintBinding? = null
    private val binding get() = _binding!!

    private var preview: ItemPrintBinding? = null

    private val viewModel: CharacterPrintViewModel by viewModels()

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        val inflater = super.onGetLayoutInflater(savedInstanceState)
        val contextThemeWrapper: Context =
            ContextThemeWrapper(requireContext(), R.style.Theme_RickAndMorty)
        return inflater.cloneInContext(contextThemeWrapper)
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View {
        val character = args.character
        _binding = FragmentCharacterPrintBinding.inflate(i, c, false).apply {
            previewDp.run { character.load(image, name, status, gender, race, origin, location) }
            root.genPreview(character)

            btnSaveCard.setOnClickListener {
                viewModel.saveCard(preview, character.name) { navigateBack() }
            }

            btnPrint.setOnClickListener {
                viewModel.sendToPrint(preview, requireActivity(), character.name) { navigateBack() }
            }
        }

        return binding.root
    }

    private fun ViewGroup.genPreview(character: CharacterParcelable) {
        preview =
            ItemPrintBinding.inflate(
                LayoutInflater.from(requireContext()),
                this,
                true
            ).apply {
                character.load(image, name, status, gender, race, origin, location)
            }
    }

}