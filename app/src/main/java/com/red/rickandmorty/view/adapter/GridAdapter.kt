package com.red.rickandmorty.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.red.rickandmorty.R
import com.red.rickandmorty.databinding.ItemCharacterBinding
import com.red.rickandmorty.view.parcelables.CharacterParcelable
import com.red.rickandmorty.view.util.loadImageIn
import com.red.rickandmorty.view.util.statusDeco

class GridAdapter(
    context: Context,
    private var items: List<CharacterParcelable> = listOf(),
    private val onItemClick: (CharacterParcelable) -> Unit,
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val dataBinding = if (view != null) ItemCharacterBinding.bind(view)
        else ItemCharacterBinding.inflate(inflater)

        dataBinding.apply {
            val item = items[position]

            item.loadImageIn(image)

            status.text = item.status
            item.statusDeco(status)

            name.text = item.name

            val speciesAndGenderText = "${item.species}, ${item.gender}"
            speciesAndGender.text = speciesAndGenderText

            root.setOnClickListener { onItemClick(item) }
        }
        return dataBinding.root
    }

    fun updateItems(items: List<CharacterParcelable>) {
        this.items = items
        notifyDataSetChanged()
    }

}
