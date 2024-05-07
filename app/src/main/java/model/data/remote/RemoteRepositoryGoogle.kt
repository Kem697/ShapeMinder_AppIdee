package model.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.data.remote.api_model.GooglePlaces.GooglePlacesApi
import model.data.remote.api_model.GooglePlaces.GymLocationApi
import model.data.remote.api_model.GooglePlaces.GymPhotoApi
import model.data.remote.api_model.GooglePlaces.Photo
import model.data.remote.api_model.GooglePlaces.Place
import model.data.remote.api_model.GooglePlaces.PlacesApi

class RemoteRepositoryGoogle(
    private val googleApi: GooglePlacesApi,
    private val gymPhotoApi: GymLocationApi
) {
    private var _getGym = MutableLiveData<List<Place>>()

    val getGym : LiveData<List<Place>>

        get() = _getGym



    private var _getGymPhotos = MutableLiveData<String>()

    val getGymPhotos : LiveData<String>

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




    suspend fun getGymLocationPhoto(photoReference: String) {
       /* var result  = googleApi.retrofitService.getGymPlacePhoto(photoReference = photoReference)
        var tag ="Bilder??"
        Log.i(tag, "Get Gym Phot :$result")
        return googleApi.retrofitService.getGymPlacePhoto(photoReference = photoReference)


    }*/
        try {
            val result = googleApi.retrofitService.getGymPlacePhoto(photoReference= photoReference)
            var myString = result.toString()
//            _getGymPhotos.postValue(result)
            var tag ="API??"
            Log.i(tag, "Get Gym Photo :$myString")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"Find Photo Gym: Fehler bei der API Anfrage!: $e")
        }
    }
}