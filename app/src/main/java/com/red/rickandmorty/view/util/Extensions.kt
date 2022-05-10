package com.red.rickandmorty.view.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.red.rickandmorty.R

fun Fragment.navigateTo(directions: NavDirections) {
    lifecycleScope.launchWhenStarted {
        try {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(directions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Fragment.navigateBack() {
    lifecycleScope.launchWhenStarted {
        Navigation.findNavController(requireActivity(), R.id.fragment_container)
            .popBackStack()
    }
}