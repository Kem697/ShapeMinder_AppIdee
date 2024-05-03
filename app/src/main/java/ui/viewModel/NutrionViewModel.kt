package ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.data.local.model.myNutrion.FoodFinderCategory
import model.data.remote.OpenFoodFactsApi
import model.data.remote.RemoteRepository
import model.data.remote.api_model.openFoodFacts.Product
import model.data.remote.api_model.openFoodFacts.ScannedFoodResponse

class NutrionViewModel(application: Application) : AndroidViewModel(application) {


    private val remoteRepository = RemoteRepository(OpenFoodFactsApi)

    private var groceryCategories = remoteRepository.groceryCategories



    private var _foodCategories = MutableLiveData(groceryCategories)
    val foodCategories: LiveData<List<FoodFinderCategory>>
        get() = _foodCategories


    private var _selectedFood = MutableLiveData<Product>()
    val selectedFood: LiveData<Product>
        get() = _selectedFood



    private val _selectedContentTitle = MutableLiveData<String>()
    val selectedContentTitle: LiveData<String>
        get() = _selectedContentTitle



    private val _searchFood = remoteRepository.getFood
    val searchFood: MutableLiveData<List<Product>>
        get() = _searchFood


    private var _savedFoods = MutableLiveData<MutableList<Product>>()
    val savedFoods: MutableLiveData<MutableList<Product>>

        get() = _savedFoods


    private val _scannedFood = remoteRepository.scannedFood

    val scannedFood : MutableLiveData<Product>

        get() =  _scannedFood



    var category : String = ""
    var country : String = ""



    /*EN:
    * These functions are related to issues through the api call. */

    /*Open Food Fact Api*/




    fun searchFood() {
        viewModelScope.launch {
            remoteRepository.searchFood(category,country)
        }
    }

    fun searchFoodByBarcode(barcode: String) {
        viewModelScope.launch {
            remoteRepository.searchFoodByBarcode(barcode)
        }
    }

    fun selectedFood(selectedFood: Product) {
        _selectedFood.value = selectedFood
    }

    fun setCountyAndCategory (foodCat: String, foodOrigin: String){
        category = foodCat
        country = foodOrigin
    }

    fun getFoodTitle(bodypart: String) {
        _selectedContentTitle.value = bodypart
    }


    fun retrieveNaturalFoodList(nonFilteredList: List<Product>) {
        _searchFood.value = nonFilteredList
    }

    fun filterFoodInCategorieByTitle(userInput: String, orginalFoodList: List<Product>) {
        viewModelScope.launch {
            if (userInput.isNotEmpty()) {
                val filteredFood = _searchFood.value?.filter { food ->
                    food.productNameDe?.startsWith(userInput, ignoreCase = true) ?: false
                }

                if (filteredFood != null) {
                    _searchFood.value = filteredFood
                    Log.e("Filter", "Filtered food list: $filteredFood")
                }
            } else {
                retrieveNaturalFoodList(orginalFoodList)
            }
        }
    }


    fun sortFoodsByAlphabet(sort: Boolean) {
        viewModelScope.launch {
            val filteredExercises = _searchFood.value
            val sortedExercises = if (sort) {
                filteredExercises?.sortedByDescending { it.productNameDe }
            } else {
                filteredExercises?.sortedBy { it.productNameDe }
            }
            _searchFood.value = sortedExercises
        }
    }


    fun isSaved(saved: Boolean, food: Product) {
        val savedFood = _savedFoods.value ?: mutableListOf()

        if (saved) {
            savedFood.add(food)
            var tag = "Fehler"
            Log.e(
                tag,
                "Lebensmittel wird gespeichert!!:${food} Zustand: ${saved}. Die Liste enthält: ${savedFood.size}"
            )
        } else {
            savedFood.remove(food)
            var tag = "Fehler"
            Log.e(tag, "Lebensmittel wird entfernt!!:${food} Zustand: ${saved} ${savedFood}")
        }

        _savedFoods.value = savedFood
    }
}