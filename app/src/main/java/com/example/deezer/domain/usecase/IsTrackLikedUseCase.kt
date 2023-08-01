package com.example.deezer.domain.usecase

import com.example.deezer.data.repository.TrackRepository
import javax.inject.Inject

class IsTrackLikedUseCase @Inject constructor(private val repository: TrackRepository) {
    suspend operator fun invoke(trackId: Long)=repository.isTrackLiked(trackId)
}