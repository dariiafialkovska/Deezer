package com.example.deezer.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deezer.data.model.response.TableConstants.TABLE_NAME
import com.example.deezer.data.model.response.TrackModel

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedTrack(trackModel: TrackModel)

    @Delete
    suspend fun deleteLikedTrack(trackModel: TrackModel)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllLikedTracks(): List<TrackModel>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_NAME WHERE id= :trackId LIMIT 1)")
    suspend fun isTrackLiked(trackId:Long):Boolean

    @Query("SELECT id FROM $TABLE_NAME")
    suspend fun getAllLikedTrackIds():List<Long>
}