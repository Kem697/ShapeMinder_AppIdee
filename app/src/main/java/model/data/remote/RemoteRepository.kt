package model.data.remote

import android.util.Log
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

    suspend fun isTokenExpired(tokenInBuffer: AccessToken?){
        if (tokenInBuffer==null){
            insertToken()
        } else{
            try {
                var dateFormater = DateTimeFormatter.ISO_DATE_TIME
                var localDate = LocalDateTime.now()
                var tokenTime = tokenInBuffer.time
                var currentTokenTime = LocalDateTime.parse(tokenTime,dateFormater)
                var timeRequirement = Duration.between(currentTokenTime,localDate).toHours()
                if (timeRequirement>=24){
                    insertToken()
                } else {
                    var tag = "Aktueller Token??"
                    Log.i(tag,"Token-Status: ${tokenInBuffer.accessToken}")
                }
            } catch (e:Exception){
                var tag = "Token abgelaufen??"
                Log.i(tag,"Fehler beim Überprüfen des Token-Status: $e")
            }
        }
    }



    suspend fun insertToken(){
        try {
            var result = foodTokenApi.retrofitService.generateToken()
            var newToken = AccessToken(0,result.accessToken,LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
            tokenDatabase.fatSecretTokenDao.insert(newToken)
            var tag = "Token Neu??"
            Log.i(tag,"Token wurde generiert!: ${newToken.accessToken}")
        } catch (e: Exception){
            var tag = "Fehler Tokenerstellung??"
            Log.i(tag,"Token konnte nicht erstellt werden: ! $e")
        }
    }


    suspend fun getTokenFromDatabase (): List<AccessToken>{
        try {
            return tokenDatabase.fatSecretTokenDao.getAll()
        } catch (e:Exception){
            var tag = "Request Token??"
            Log.i(tag,"Fehler bei der Token erstellung")
            return listOf()
        }
    }



suspend fun foodExampleDetail(accessToken:String){
    try {
        var result = fatSecretApi.retrofitService.exampleFoodDetails(authToken = "Bearer $accessToken", foodId = 33691)
        var tag ="API Test??"

        Log.i(tag,"Erfolgreicher: $result")
    } catch (e:Exception){
        var tag ="API Anfrage??"
        Log.i(tag,"Fehler bei der API Anfrage!: $e")
    }
}

}