package com.example.deezer.presentation.tracklist

import com.example.deezer.domain.model.ListTrackModel

interface TrackOnItemClickListener {
    fun onItemClicked(previewUrl: String)
    fun onFavoriteButtonClicked(track: ListTrackModel)
}