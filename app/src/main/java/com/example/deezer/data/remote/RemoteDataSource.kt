package com.example.deezer.data.remote

import com.example.deezer.data.model.response.AlbumResponse
import com.example.deezer.data.model.response.ArtistResponse
import com.example.deezer.data.model.response.GenreResponse
import com.example.deezer.data.model.response.TrackResponse

interface RemoteDataSource {
    suspend fun fetchAllGenres(limit: Int): GenreResponse?

    suspend fun fetchGenreArtists(genreId: Int): ArtistResponse?

    suspend fun fetchAllAlbums(artistId: Int): AlbumResponse?

    suspend fun fetchAllTracks(albumId: Long): TrackResponse?
}
