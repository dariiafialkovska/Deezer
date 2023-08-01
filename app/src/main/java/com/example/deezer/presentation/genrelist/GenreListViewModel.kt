package com.example.deezer.presentation.genreList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezer.common.Result
import com.example.deezer.domain.model.ListGenreModel
import com.example.deezer.domain.usecase.FetchAllGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GenreListViewModel @Inject constructor(private val fetchAllGenresUseCase: FetchAllGenresUseCase):ViewModel() {
    var mutableGenreData = MutableLiveData<ArrayList<ListGenreModel>?>()
        private set

    var mutableProgressData= MutableLiveData<Boolean>()
        private set

    var mutableErrorData = MutableLiveData<Boolean>()
        private set

    fun fetchAllGenres(){
        viewModelScope.launch{

            fetchAllGenresUseCase.invoke().collect{
                when(it){
                    is Result.Success ->{
                        mutableGenreData.value=it.data
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