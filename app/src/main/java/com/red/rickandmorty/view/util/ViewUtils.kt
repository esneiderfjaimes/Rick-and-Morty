package com.red.rickandmorty.view.util

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.transform.CircleCropTransformation
import com.red.rickandmorty.view.parcelables.CharacterParcelable

fun CharacterParcelable.load(
    image: ImageView,
    name: TextView,
    status: TextView,
    gender: TextView,
    race: TextView,
    origin: TextView,
    location: TextView,
) {
    this.let { character ->
        loadImageIn(image)
        status.text = character.status
        name.text = character.name
        gender.text = character.gender
        race.text = character.species
        origin.text = character.origin.name
        location.text = character.location.name
    }
}

fun CharacterParcelable.loadImageIn(image: ImageView) {
    image.load(this.image) {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(ImageDefault.getDrawable(image))
        error(ImageDefault.getDrawable(image))
    }
}

fun CharacterParcelable.statusDeco(
    txtStatus: TextView,
) {
    when (status) {
        "Alive" -> txtStatus.setTextColor(Color.GREEN)
        "Dead" -> txtStatus.setTextColor(Color.RED)
        else -> txtStatus.setTextColor(Color.GRAY)
    }
}