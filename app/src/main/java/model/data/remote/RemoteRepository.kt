package model.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import model.data.remote.api_model.token.AccessToken
import model.data.local.FatSecretDatabase
import java.lang.Exception
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RemoteRepository (
    private val fatSecretApi: FoodApi,
    private val foodTokenApi: FoodTokenApi,
    private val tokenDatabase: FatSecretDatabase)
{



    private val accessToken: LiveData<AccessToken> = tokenDatabase.fatSecretTokenDao.getToken()


    suspend fun isTokenExpired(){
        var dateFormater = DateTimeFormatter.ISO_DATE_TIME
        var localDate = LocalDateTime.now()
        var tokenTime = accessToken.value?.time
        var currentTokenTime = LocalDateTime.parse(tokenTime?:"2024-01-01T00:00:00",dateFormater)
        if (Duration.between(currentTokenTime,localDate).toHours()>=24){
            insertToken()
        }
    }



    suspend fun insertToken(){

        try {
            var result = foodTokenApi.retrofitService.generateToken()
            var newToken = AccessToken(0,result.accessToken,LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            tokenDatabase.fatSecretTokenDao.insert(newToken)
            var tag = "Token Neu??"
            Log.i(tag,"Token wurde generiert!: ${accessToken.value?.accessToken}")
        } catch (e: Exception){
            var tag = "Fehler Tokenerstellung??"
            Log.i(tag,"Token konnte nicht erstell werden: ! $e")
        }
    }


    suspend fun getToken (){
        try {
            tokenDatabase.fatSecretTokenDao.getToken()
        } catch (e:Exception){
            var tag = "Request Token??"
            Log.i(tag,"Fehler bei der Token erstellung")
        }
    }



suspend fun foodExampleDetail(){
    try {
        var result = fatSecretApi.retrofitService.exampleFoodDetails(authToken = "Bearer ${accessToken.value!!.accessToken}", foodId = 33691)
        var tag ="API Test??"

        Log.i(tag,"Erfolgreicher: $result")
    } catch (e:Exception){
        var tag ="API Anfrage??"
        Log.i(tag,"Fehler bei der API Anfrage!: $e")
    }
}



}