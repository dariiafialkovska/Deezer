package com.example.deezer.domain.model

data class ListTrackModel (
    val id : Long?=null,
    val title: String?=null,
    val duration: String?=null,
    val preview: String?=null,
    val md5_image: String?=null,
    var isLiked: Boolean=false
    )
