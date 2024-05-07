package model.data.remote

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.data.remote.api_model.GooglePlaces.GooglePlacesApi
import model.data.remote.api_model.GooglePlaces.Photo
import model.data.remote.api_model.GooglePlaces.Place

class RemoteRepositoryGoogle(
    private val googleApi: GooglePlacesApi
) {
    private var _getGym = MutableLiveData<List<Place>>()

    val getGym : LiveData<List<Place>>

        get() = _getGym



    private var _getGymPhotos = MutableLiveData<List<Place>>()

    val getGymPhotos : LiveData<List<Place>>

        get() = _getGymPhotos




    suspend fun getGymLocation(){
        try {
            val result = googleApi.retrofitService.getGymPlace()
            _getGym.postValue(result.results)
            var tag ="API??"
            Log.i(tag, "Get Gym Call?? :$result")
            println("Get Gym Call?? :$result")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"Find Gym: Fehler bei der API Anfrage!: $e")
        }
    }


    suspend fun getGymLocationPhoto(photoReference: String, attributions: List<String>){
        try {
            val result = googleApi.retrofitService.getGymPlacePhoto(photoReference= photoReference, attributions = attributions)
            _getGymPhotos.postValue(result.results)
            var tag ="API??"
            Log.i(tag, "Get Gym Phot :$result")
            println("Get Gym Call?? :$result")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"Find Photo Gym: Fehler bei der API Anfrage!: $e")
        }
    }
}