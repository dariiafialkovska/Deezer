package com.example.deezer.domain.usecase
import com.example.deezer.common.Result
import com.example.deezer.data.repository.DeezerRepository
import com.example.deezer.util.extensions.toListArtistModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAllGenreArtistsUseCase @Inject constructor(private val repository: DeezerRepository){
    suspend operator fun invoke(genreId: Int)=flow{

        emit(Result.Loading)

        repository.fetchGenreArtists(genreId)?.let {
            emit(Result.Success(it.toListArtistModel()))
        }?: run{
            emit(Result.Error(null))
        }
    }


}