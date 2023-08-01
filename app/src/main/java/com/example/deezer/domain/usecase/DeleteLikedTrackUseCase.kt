package com.example.deezer.domain.usecase

import com.example.deezer.data.model.response.TrackModel
import com.example.deezer.data.repository.TrackRepository
import javax.inject.Inject

class DeleteLikedTrackUseCase @Inject constructor(private val repository: TrackRepository) {
    suspend operator fun invoke(trackModel: TrackModel)=repository.deleteLikedTrack(trackModel)
}