package com.example.deezer.presentation.albumlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezer.domain.model.ListAlbumModel
import com.example.deezer.domain.usecase.FetchAllAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.deezer.common.Result


@HiltViewModel
class AlbumListViewModel @Inject constructor(private val fetchAllAlbumsUseCase: FetchAllAlbumsUseCase):
 ViewModel(){
     var mutableAlbumData= MutableLiveData<ArrayList<ListAlbumModel>?>()
         private set

     var mutableProgressData= MutableLiveData<Boolean>()
         private set

     var mutableErrorData= MutableLiveData<Boolean>()
         private set

     fun fetchAllAlbums( artistId:Int){

         viewModelScope.launch {
             fetchAllAlbumsUseCase.invoke(artistId).collect{
                 when(it){
                     is Result.Success ->{
                         mutableAlbumData.value=it.data
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
