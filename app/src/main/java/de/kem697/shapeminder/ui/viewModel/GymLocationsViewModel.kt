package de.kem697.shapeminder.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.kem697.shapeminder.model.data.remote.RemoteRepositoryGoogle
import de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces.GooglePlacesApi
import kotlinx.coroutines.launch


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