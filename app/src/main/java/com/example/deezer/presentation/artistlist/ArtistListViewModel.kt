package com.example.deezer.presentation.artistlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezer.domain.model.ListArtistModel
import com.example.deezer.domain.usecase.FetchAllGenreArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.deezer.common.Result


@HiltViewModel
class ArtistListViewModel @Inject constructor(private val fetchAllGenreArtistsUseCase: FetchAllGenreArtistsUseCase):
    ViewModel(){

        var mutableArtistData = MutableLiveData<ArrayList<ListArtistModel>?>()
            private set

        var mutableProgressData= MutableLiveData<Boolean>()
            private set

        var mutableErrorData= MutableLiveData<Boolean>()
            private set


        fun fetchGenreArtists (
            genreId: Int
        ){

            viewModelScope.launch {
                fetchAllGenreArtistsUseCase.invoke(genreId).collect{
                    when(it){
                        is Result.Success ->{
                            mutableArtistData.value=it.data
                            mutableProgressData.value=false
                            mutableErrorData.value=false
                        }
                        is Result.Error ->{
                            mutableErrorData.value=true
                            mutableProgressData.value=false
                        }
                        is Result.Loading ->{
                            mutableProgressData.value=true
                            mutableErrorData.value=false
                        }
                    }
                }
            }
        }

    }

