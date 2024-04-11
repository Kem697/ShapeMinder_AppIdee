package model.data.remote

import android.util.Log
import java.lang.Exception

class RemoteRepository (private val fatSecretApi: FoodApi) {



suspend fun foodExampleDetail(){
    try {
        var result = fatSecretApi.retrofitService.exampleFoodDetails(authToken = accessToken)
        var tag ="API Test??"
        Log.i(tag,"Erfolgreicher: $result")
    } catch (e:Exception){
        var tag ="API Anfrage??"
        Log.i(tag,"Fehler bei der API Anfrage!: $e")
    }
}



}