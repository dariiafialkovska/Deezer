package com.example.deezer.presentation.likedlist

import com.example.deezer.domain.model.ListTrackModel

interface OnDeleteItemClickListener {
    fun onDeleteClicked(track : ListTrackModel)
}