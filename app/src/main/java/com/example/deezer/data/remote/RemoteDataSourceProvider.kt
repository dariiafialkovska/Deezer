package com.example.deezer.data.remote

import android.util.Log
import com.example.deezer.data.model.response.AlbumResponse
import com.example.deezer.data.model.response.ArtistResponse
import com.example.deezer.data.model.response.GenreResponse
import com.example.deezer.data.model.response.TrackResponse
import com.example.deezer.data.network.DeezerService
import javax.inject.Inject

class RemoteDataSourceProvider @Inject constructor(private val service: DeezerService): RemoteDataSource{
    override suspend fun fetchAllGenres(limit: Int): GenreResponse{
        val response= service.fetchAllGenres(limit)
        Log.d("API",response.toString())
        return response
    }

    override suspend fun fetchGenreArtists(genreId: Int): ArtistResponse? {
        val response=service.fetchGenreArtists(genreId)
        Log.d("API",response.toString())
        return service.fetchGenreArtists(genreId)
    }


    override suspend fun fetchAllAlbums(artistId: Int): AlbumResponse? {
        val response=service.fetchAllAlbums(artistId)
        Log.d("Album API",response.toString())
        return response
    }

    override suspend fun fetchAllTracks(albumId: Long): TrackResponse? {
        val response=service.fetchAllTracks(albumId)
        Log.d("Track API",response.toString())
        return response
    }
}