package com.example.deezer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deezer.data.model.response.TrackModel


@Database(entities = [TrackModel::class],version=2, exportSchema = false)
abstract class TrackDatabase: RoomDatabase() {
    abstract fun likedTrackDao(): TrackDao
}