package com.example.deezer.domain.usecase

import com.example.deezer.data.repository.DeezerRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.deezer.common.Result
import com.example.deezer.util.extensions.toListTrackModel


class FetchAllTracksUseCase @Inject constructor(private val repository: DeezerRepository){
    suspend operator fun invoke (albumId: Long)=flow{
        emit(Result.Loading)

        repository.fetchAllTracks(albumId)?.let {
            emit(Result.Success(it.toListTrackModel()))

        }?: run{
            emit(Result.Error(null))
        }
    }
}