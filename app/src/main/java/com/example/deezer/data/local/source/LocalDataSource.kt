package com.example.deezer.data.local.source

import com.example.deezer.core.data.BaseLocalDataSource
import com.example.deezer.data.local.db.TrackDao
import com.example.deezer.data.model.response.TrackModel
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val trackDao: TrackDao): BaseLocalDataSource(){

    suspend fun insertLikedTrack(trackModel: TrackModel)=
        performDatabaseOperation {
            trackDao.insertLikedTrack(trackModel)
        }

    suspend fun deleteLikedTrack(trackModel: TrackModel)=
        performDatabaseOperation {
            trackDao.deleteLikedTrack(trackModel)
        }

    suspend fun getAllLikedTracks()=
        performDatabaseOperation {
            trackDao.getAllLikedTracks()
        }


    suspend fun isTrackLiked(trackId: Long)=
        performDatabaseOperation {
            trackDao.isTrackLiked(trackId)
        }

    suspend fun getAllLikedTrackIds()=
        performDatabaseOperation {
            trackDao.getAllLikedTrackIds()
        }
}