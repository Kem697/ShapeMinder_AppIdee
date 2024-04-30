package model.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.shapeminder_appidee.R
import model.data.local.model.myNutrion.FoodFinderCategory
import model.data.remote.api_model.openFoodFacts.Product
import kotlin.Exception

class RemoteRepository (
    private val openFoodApi: OpenFoodFactsApi,)
{

    var groceryCategories = loadGroceryCategories()


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



    fun loadGroceryCategories(): List<FoodFinderCategory>{
        return listOf(
            FoodFinderCategory(
                R.string.gc_grain_and_corn,
                R.drawable.foodcat1_noodles_img,"Getreide",true),
            FoodFinderCategory(
                R.string.gc_fruits_and_vegetable,
                R.drawable.foodcat4_fruits_and_vegetables,"Obst und Gemüse",true),
            FoodFinderCategory(
                R.string.gc_milk_and_eg,
                R.drawable.foodcat5_milk_and_eggs,"Molkerei und Eier",true),
            FoodFinderCategory(
                R.string.gc_oil_and_fats,
                R.drawable.foodcat2_oil_img,"Öle und Fette",true),
            FoodFinderCategory(
                R.string.gc_meat_and_fish,
                R.drawable.foodcat3_meat_img,"Fleisch und Fisch",true),
            FoodFinderCategory(R.string.gc_sweets, R.drawable.foodcat6_sweets,"Süssigkeiten",true)
        )
    }


}