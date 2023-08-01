package com.example.deezer.presentation.tracklist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezer.common.Result
import com.example.deezer.data.model.response.TrackModel
import com.example.deezer.domain.model.ListTrackModel
import com.example.deezer.domain.usecase.DeleteLikedTrackUseCase
import com.example.deezer.domain.usecase.FetchAllTracksUseCase
import com.example.deezer.domain.usecase.GetAllLikedTrackIdsUseCase
import com.example.deezer.domain.usecase.GetAllLikedTracksUseCase
import com.example.deezer.domain.usecase.InsertLikedTrackUseCase
import com.example.deezer.domain.usecase.IsTrackLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackListViewModel @Inject constructor(
    private val fetchAllTracksUseCase: FetchAllTracksUseCase,
    private val getAllLikedTrackIdsUseCase: GetAllLikedTrackIdsUseCase,
    private val insertLikedTrackUseCase: InsertLikedTrackUseCase,
    private val deleteLikedTrackUseCase: DeleteLikedTrackUseCase,
    private val getAllLikedTracksUseCase: GetAllLikedTracksUseCase,
    private val isTrackLikedUseCase: IsTrackLikedUseCase
    ): ViewModel() {

    var mutableTrackData= MutableLiveData<ArrayList<ListTrackModel>?>()
        private set

    var mutableProgressData= MutableLiveData<Boolean>()
        private set

    var mutableErrorData= MutableLiveData<Boolean>()
        private set

    var mutableStateTrackData=MutableLiveData<Boolean>()
        private set

    var mutableError= MutableLiveData<String>()
        private set

    var mutableIsLoading=MutableLiveData<Boolean>()
        private set

    var mutableListData=MutableLiveData<List<ListTrackModel>?>()
        private set

    var mutableLongListData=MutableLiveData<List<Long>>()
        private set



    fun getAllLikedTracksIds(){
        viewModelScope.launch{
            getAllLikedTrackIdsUseCase.invoke()
                .onSuccess {
                    mutableLongListData.value=it
                }
                .onFailure {
                    mutableIsLoading.value=false
                    mutableError.value=it.orEmpty()
                }
        }
    }

    fun getAllLikedTracks(){
        viewModelScope.launch{
            getAllLikedTracksUseCase.invoke()
                .onSuccess { it ->
                    val listTrackModels = it?.map {
                        ListTrackModel(
                            id = it.id,
                            title = it.title,
                            duration = it.duration,
                            preview = it.preview,
                            md5_image = it.md5_image,
                            isLiked = true
                        )
                    }

                    mutableListData.value = listTrackModels
                }
                .onFailure {
                    mutableIsLoading.value=false
                    mutableError.value=it.orEmpty()
                }
        }
    }



    fun insertTrack(trackModel: TrackModel){
        viewModelScope.launch {
            insertLikedTrackUseCase.invoke(trackModel)
                .onSuccess {
                    mutableIsLoading.value=false

                }
                .onFailure {
                    mutableIsLoading.value=false
                    mutableError.value=it.orEmpty()
                }
        }
    }




    fun deleteTrack(trackModel: TrackModel){
        viewModelScope.launch {
            deleteLikedTrackUseCase.invoke(trackModel)
                .onSuccess {
                    mutableIsLoading.value=false
                }
                .onFailure {
                    mutableIsLoading.value=false
                    mutableError.value=it.orEmpty()
                }
        }
    }



    fun isLikedTrack(trackId:Long?){
        viewModelScope.launch {
            if (trackId != null) {
                isTrackLikedUseCase.invoke(trackId)
                    .onSuccess {
                        mutableStateTrackData.value=it
                    }
                    .onFailure {
                        mutableIsLoading.value=false
                        mutableError.value=it.orEmpty()
                    }
            }
        }

    }

    fun fetchAllTracks(albumId: Long){
        viewModelScope.launch {
            fetchAllTracksUseCase.invoke(albumId).collect{
                when(it){
                    is Result.Success ->{
                        mutableTrackData.value=it.data
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