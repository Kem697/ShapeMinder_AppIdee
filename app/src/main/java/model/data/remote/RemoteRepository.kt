package model.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import model.data.remote.api_model.openFoodFacts.Product
import kotlin.Exception

class RemoteRepository (
    private val openFoodApi: OpenFoodFactsApi,)
{


    /*OpenFoodFact Api*/


    private var _getFood = MutableLiveData<List<Product>>()

    val getFood : MutableLiveData<List<Product>>

        get() = _getFood


    suspend fun searchFood( foodCatEn: String,countryTagEn: String){
        try {
            val result = openFoodApi.retrofitService.searchFood(foodCatEn, countryTagEn)
            _getFood.postValue(result.products)
            println("Search Food Call?? :$result")
        } catch (e:Exception){
            var tag ="API??"
            Log.i(tag,"SerchFood: Fehler bei der API Anfrage!: $e")
        }
    }


}