package com.example.deezer.data.repository

import com.example.deezer.data.remote.RemoteDataSourceProvider
import javax.inject.Inject

class DeezerRepository @Inject constructor(private val remoteDataSourceProvider: RemoteDataSourceProvider){

    suspend fun fetchAllGenres(limit:Int)= remoteDataSourceProvider.fetchAllGenres(limit)

    suspend fun fetchGenreArtists(genreId: Int)= remoteDataSourceProvider.fetchGenreArtists(genreId)

    suspend fun fetchAllAlbums(artistId: Int)= remoteDataSourceProvider.fetchAllAlbums(artistId)

    suspend fun fetchAllTracks(albumId: Long)=remoteDataSourceProvider.fetchAllTracks(albumId)



}

