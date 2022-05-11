package com.red.rickandmorty.view.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.red.rickandmorty.R

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

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

fun <K, V> Map<K, V>.toMutableList() = toList().toMutableList()
fun <K, V> Map<K, V>.keysList() = keys.toList()