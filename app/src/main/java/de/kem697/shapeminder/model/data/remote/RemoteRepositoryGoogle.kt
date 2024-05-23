package de.kem697.shapeminder.model.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces.GooglePlacesApi
import de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces.Place


class RemoteRepositoryGoogle(
    private val googleApi: GooglePlacesApi,
) {
    private var _getGym = MutableLiveData<List<Place>>()

    val getGym : LiveData<List<Place>>

        get() = _getGym



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





}