package com.example.deezer.data.di

import com.example.deezer.data.repository.TrackRepository
import com.example.deezer.data.repository.TrackRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideTrackRepository(trackRepositoryImpl: TrackRepositoryImpl):TrackRepository
}