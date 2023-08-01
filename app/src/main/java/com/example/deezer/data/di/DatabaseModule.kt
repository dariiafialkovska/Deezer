package com.example.deezer.data.di

import com.example.deezer.data.local.db.TrackDao
import android.content.Context
import androidx.room.Room
import com.example.deezer.data.local.db.TrackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTrackDatabase(@ApplicationContext context: Context): TrackDatabase {
        return Room.databaseBuilder(
            context,
            TrackDatabase::class.java,
            "track_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideLikedTrackDuo(trackDatabase: TrackDatabase): TrackDao {
        return trackDatabase.likedTrackDao()
    }
}