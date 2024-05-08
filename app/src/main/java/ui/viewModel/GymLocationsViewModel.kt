package ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.data.remote.RemoteRepositoryGoogle
import model.data.remote.api_model.GooglePlaces.GooglePlacesApi

class GymLocationsViewModel: ViewModel() {

    private val googleRepository = RemoteRepositoryGoogle(GooglePlacesApi)

    val getGymLocations = googleRepository.getGym



    init {
        getGymLocations()
    }


    fun getGymLocations(){
        viewModelScope.launch {
            googleRepository.getGymLocation()
        }
    }

}