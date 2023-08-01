package com.example.deezer.util.extensions

import com.example.deezer.data.model.response.AlbumResponse
import com.example.deezer.data.model.response.ArtistResponse
import com.example.deezer.data.model.response.GenreResponse
import com.example.deezer.data.model.response.TrackResponse
import com.example.deezer.domain.model.ListAlbumModel
import com.example.deezer.domain.model.ListArtistModel
import com.example.deezer.domain.model.ListGenreModel
import com.example.deezer.domain.model.ListTrackModel

fun GenreResponse.toListGenreModel(): ArrayList<ListGenreModel> {
    var list= arrayListOf<ListGenreModel>()

    this.data?.forEach{
        list.add(ListGenreModel(
            it.id,
            it.name,
            it.picture_medium,
            it.type

        ))
    }
    return list
}


fun ArtistResponse.toListArtistModel(): ArrayList<ListArtistModel>{
    val list= arrayListOf<ListArtistModel>()

    this.data?.forEach{
        list.add(
            ListArtistModel(
            it.id,
            it.name,
            it.picture_medium,
            it.type
        )
        )
    }
    return list
}

fun AlbumResponse.toListAlbumModel(): ArrayList<ListAlbumModel>{
    val list= arrayListOf<ListAlbumModel>()

    this.data?.forEach{
        list.add(
            ListAlbumModel(
                it.id,
                it.title,
                it.cover_medium,
                it.record_type,
                it.release_date
            )
        )
    }
    return list
}




fun TrackResponse.toListTrackModel(): ArrayList<ListTrackModel>{
    val list= arrayListOf<ListTrackModel>()

    this.data?.forEach{
        list.add(
            ListTrackModel(
                it.id,
                it.title,
                it.duration,
                it.preview,
                it.md5_image
                )
        )
    }
    return list
}
