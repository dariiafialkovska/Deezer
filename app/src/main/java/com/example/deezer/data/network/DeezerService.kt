package com.example.deezer.data.network

import com.example.deezer.data.model.response.AlbumResponse
import com.example.deezer.data.model.response.ArtistResponse
import com.example.deezer.data.model.response.GenreResponse
import com.example.deezer.data.model.response.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    @GET("/genre")
    suspend fun fetchAllGenres(@Query("limit") limit: Int): GenreResponse

    @GET("/genre/{genre_id}/artists")
    suspend fun fetchGenreArtists(@Path("genre_id") genreId:Int): ArtistResponse

    @GET("/artist/{artist_id}/albums")
    suspend fun fetchAllAlbums(@Path("artist_id") artistId:Int): AlbumResponse

    @GET("/album/{album_id}/tracks")
    suspend fun fetchAllTracks(@Path("album_id") albumId: Long): TrackResponse
}

