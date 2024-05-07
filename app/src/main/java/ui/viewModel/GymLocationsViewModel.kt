package ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.data.remote.RemoteRepositoryGoogle
import model.data.remote.api_model.GooglePlaces.GooglePlacesApi
import model.data.remote.api_model.GooglePlaces.GymLocationApi

class GymLocationsViewModel: ViewModel() {

    private val googleRepository = RemoteRepositoryGoogle(GooglePlacesApi,GymLocationApi)

    val getGymLocations = googleRepository.getGym


    val getGymLocationPhotos = googleRepository.getGym



    init {
        getGymLocations()
    }


    fun getGymLocations(){
        viewModelScope.launch {
            googleRepository.getGymLocation()
        }
    }


    fun getGymLocationPhotos(photoReference:String) {
//        var imageString = ""
        viewModelScope.launch{
             googleRepository.getGymLocationPhoto(photoReference)
//            imageString = googleRepository.getGymLocationPhoto(photoReference)
        }
    }
}