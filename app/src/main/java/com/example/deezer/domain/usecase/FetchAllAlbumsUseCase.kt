package com.example.deezer.domain.usecase

import com.example.deezer.data.repository.DeezerRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.deezer.common.Result
import com.example.deezer.util.extensions.toListAlbumModel


class FetchAllAlbumsUseCase @Inject constructor(private val repository: DeezerRepository){
suspend operator fun invoke(artistId: Int)=flow{
    emit(Result.Loading)

    repository.fetchAllAlbums(artistId)?.let{
        emit(Result.Success(it.toListAlbumModel()))
    }?: run{
        emit(Result.Error(null))
    }
}

}