package com.red.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.red.code015.data.Repository
import com.red.rickandmorty.view.parcelables.EpisodeParcelable
import com.red.rickandmorty.view.parcelables.toParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import rickandmorty.domain.models.Result
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _episode: MutableLiveData<EpisodeParcelable> = MutableLiveData()
    val episode: LiveData<EpisodeParcelable> = _episode

    fun searchEpisodes(ids: List<Int>) {
        viewModelScope.launch {
            repository.getEpisodes(*ids.toIntArray())
                .mapNotNull { // just collect results success
                    if (it is Result.Success)
                        it.value.toParcelable()
                    else null
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _episode.postValue(it)
                }
        }
    }
}