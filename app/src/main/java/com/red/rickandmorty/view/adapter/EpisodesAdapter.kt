package com.red.rickandmorty.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.red.rickandmorty.databinding.ItemEpisodeBinding
import com.red.rickandmorty.view.parcelables.EpisodeParcelable

typealias PairEpisode = Pair<Int, EpisodeParcelable?>

class EpisodesAdapter(
    private var items: MutableList<PairEpisode> = mutableListOf(),
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            root.setBackgroundColor(
                if (position % 2 == 0) Color.TRANSPARENT
                else Color.argb(12, 128, 128, 128)
            )

            if (item.second == null) {
                episodeAirDate.visibility = View.GONE
                episodeName.visibility = View.GONE
            } else {
                episodeAirDate.visibility = View.VISIBLE
                episodeName.visibility = View.VISIBLE
            }

            val idText = item.second?.let { episode ->
                episodeName.text = episode.name
                episodeAirDate.text = episode.airDate
                "EPISODE ${item.first} (${episode.episode})"
            } ?: "EPISODE " + item.first
            episodeId.text = idText
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateEpisode(episode: EpisodeParcelable) {
        val index = items.indexOfFirst { it.first == episode.id }
        if (index != -1) {
            items[index] = episode.id to episode
            notifyItemChanged(index)
        }
    }

}