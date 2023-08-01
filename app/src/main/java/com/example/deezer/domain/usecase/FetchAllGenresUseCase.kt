package com.example.deezer.domain.usecase
import com.example.deezer.common.Result
import com.example.deezer.data.repository.DeezerRepository
import com.example.deezer.util.Constants
import com.example.deezer.util.extensions.toListGenreModel
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class FetchAllGenresUseCase @Inject constructor(private val repository: DeezerRepository) {
    suspend operator fun invoke()= flow{

        emit(Result.Loading)

        repository.fetchAllGenres(Constants.LIMIT)?.let{

            emit(Result.Success(it.toListGenreModel()))

        }?: run{
            emit(Result.Error(null))
        }

    }


}