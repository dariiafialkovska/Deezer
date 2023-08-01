package com.example.deezer.data.repository

import com.example.deezer.core.data.DataResult
import com.example.deezer.data.model.response.TrackModel


interface TrackRepository {
    suspend fun insertLikedTrack(trackModel: TrackModel):DataResult<Unit, String>

    suspend fun deleteLikedTrack(trackModel: TrackModel):DataResult<Unit, String>

    suspend fun getAllLikedTracks():DataResult<List<TrackModel>, String>

    suspend fun isTrackLiked(trackId: Long):DataResult<Boolean, String>

    suspend fun getAllLikedTrackIds(): DataResult<List<Long>, String>
}