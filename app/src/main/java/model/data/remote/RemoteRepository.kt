package model.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.data.remote.api_model.token.AccessToken
import model.data.local.FatSecretDatabase
import model.data.remote.api_model.Food
import model.data.remote.api_model.listOfFoodCat.FoodCategories
import model.data.remote.api_model.openFoodFacts.Product
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.Exception

class RemoteRepository (
    private val fatSecretApi: FoodApi,
    private val foodTokenApi: FoodTokenApi,
    private val openFoodApi: OpenFoodFactsApi,
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



    private var _foodRequest = MutableLiveData<List<Food>>()

    val foodRequest: LiveData<List<Food>>
        get() = _foodRequest

/*    suspend fun getFoodCategories(calories: String,protein: String,fat: String,carbohydrates: String){
        try {
            val result= fatSecretApi.retrofitService.getFoodCategories("food_categories.get.v2","DE",calories,protein,fat,carbohydrates)
            _foodRequest.postValue(listOf(result))
            var tag = "Result??"
            Log.i(tag,"Anfrage?: $result")
        } catch (e:Exception){
            var tag ="API Anfrage??"
            Log.i(tag,"Fehler bei der API Anfrage!: $e")
        }
    }*/



    private var _requestAllFoodCategories = MutableLiveData<FoodCategories>()

    val requestAllFoodCategories: LiveData<FoodCategories>
        get() = _requestAllFoodCategories
    suspend fun getAllFoodCategories(accessToken:String, region:String){
        try {
            val result = fatSecretApi.retrofitService.getAllFoodCategories(authToken = "Bearer $accessToken")
            _requestAllFoodCategories.value = result
            println("Api Call?? :$result")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"Fehler bei der API Anfrage!: $e")
        }
    }


    /*OpenFoodFact Api*/


    private var _getFood = MutableLiveData<Product>()

    val getFood : LiveData<Product>

        get() = _getFood


    suspend fun searchFood(){
        try {
            val result = openFoodApi.retrofitService.searchFood()
            _getFood.value = result
            println("Api Call?? :$result")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"Fehler bei der API Anfrage!: $e")
        }
    }


}