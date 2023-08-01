package com.example.deezer.data.repository

import com.example.deezer.data.local.source.LocalDataSource
import com.example.deezer.data.model.response.TrackModel
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):
    TrackRepository
{
    override suspend fun insertLikedTrack(trackModel: TrackModel) =
        localDataSource.insertLikedTrack(trackModel)

    override suspend fun deleteLikedTrack(trackModel: TrackModel)=
        localDataSource.deleteLikedTrack(trackModel)

    override suspend fun getAllLikedTracks() =
        localDataSource.getAllLikedTracks()

    override suspend fun isTrackLiked(trackId: Long)=
        localDataSource.isTrackLiked(trackId)

    override suspend fun getAllLikedTrackIds()=
        localDataSource.getAllLikedTrackIds()

}