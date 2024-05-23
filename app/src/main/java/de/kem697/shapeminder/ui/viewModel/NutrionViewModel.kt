package de.kem697.shapeminder.ui.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.kem697.shapeminder.model.data.local.getProductDatabase
import de.kem697.shapeminder.model.data.local.model.myNutrion.FoodFinderCategory
import de.kem697.shapeminder.model.data.local.model.myNutrion.FoodFinderSubCategory
import de.kem697.shapeminder.model.data.remote.RemoteRepositoryFood
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.OpenFoodFactsApi
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.Product
import kotlinx.coroutines.launch


class NutrionViewModel(application: Application) : AndroidViewModel(application) {

    private val productDatabase = getProductDatabase(application)



    private val remoteRepositoryFood = RemoteRepositoryFood(OpenFoodFactsApi,productDatabase)

    private var groceryCategories = remoteRepositoryFood.groceryCategories

    private var grocerySubCategories = remoteRepositoryFood.grocerySubCategories




    private var _foodCategories = MutableLiveData(groceryCategories)
    val foodCategories: LiveData<List<FoodFinderCategory>>
        get() = _foodCategories

    private var _foodSubCategories = MutableLiveData(grocerySubCategories)
    val foodSubCategories : LiveData<List<FoodFinderSubCategory>>
        get() = _foodSubCategories


    private var _selectedFood = MutableLiveData<Product>()
    val selectedFood: LiveData<Product>
        get() = _selectedFood



    private val _selectedContentTitle = MutableLiveData<String>()
    val selectedContentTitle: LiveData<String>
        get() = _selectedContentTitle


    private val _selectedFoodSubCategory = MutableLiveData<String>()
    val selectedFoodSubCategory: LiveData<String>
        get() = _selectedFoodSubCategory



    private val _searchFood = remoteRepositoryFood.getFood
    val searchFood: MutableLiveData<List<Product>>
        get() = _searchFood


    private var _savedFoods = MutableLiveData<MutableList<Product>>()
    val savedFoods: MutableLiveData<MutableList<Product>>

        get() = _savedFoods


    private val _scannedFood = remoteRepositoryFood.scannedFood

    val scannedFood : MutableLiveData<Product>

        get() =  _scannedFood



    var category : String = ""
    var country : String = ""





    /*EN:
    * These functions are related to issues through the api call. */

    /*Open Food Fact Api*/

    fun searchFood() {
        viewModelScope.launch {
            remoteRepositoryFood.searchFood(category,country)
        }
    }
    fun searchFoodByBarcode(barcode: String) {
        viewModelScope.launch {
            remoteRepositoryFood.searchFoodByBarcode(barcode)
        }
    }

    fun selectedFood(selectedFood: Product) {
        _selectedFood.value = selectedFood
    }

    fun setCountyAndCategory (foodCat: String, foodOrigin: String){
        category = foodCat
        country = foodOrigin
    }

    fun getFoodTitle(foodTitle: String) {
        _selectedContentTitle.value = foodTitle
    }

    fun getFoodSubCategory(foodTitle: String) {
        _selectedFoodSubCategory.value = foodTitle
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
            insertProduct(food)
            var tag = "Fehler"
            Log.e(
                tag,
                "Lebensmittel wird gespeichert!!:${food} Zustand: ${saved}. Die Liste enthält: ${savedFood.size}"
            )
        } else {
            savedFood.remove(food)
            deleteProduct(food)
            var tag = "Fehler"
            Log.e(tag, "Lebensmittel wird entfernt!!:${food} Zustand: ${saved} ${savedFood}")
        }

        _savedFoods.value = savedFood
    }


    /*EN:
    * These methods are related to alterations in the productDatabase*/

    init {
        setUpProductDatabase()
    }


    fun setUpProductDatabase (){
        viewModelScope.launch {
            remoteRepositoryFood.insertProduct(Product(1,null, listOf(),null,"",null,null,true))
        }
    }

    fun insertProduct (product: Product){
        viewModelScope.launch {
            remoteRepositoryFood.insertProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            remoteRepositoryFood.deleteProduct(product)
        }
    }


//    sub
fun getFoodSubCats(subcats: String, context: Context, originalFoodSubCategories: List<FoodFinderSubCategory>) {
    var tag = "SubCatFilter?"

    var currentSubCategories = grocerySubCategories
    val filteredSubCategories = currentSubCategories.filter { context.getString(it.parentCategory) == subcats }
    Log.i(tag,"Orginal List : ${_foodSubCategories.value?.size}")

    if (filteredSubCategories != null && filteredSubCategories.isNotEmpty()) {
        _foodSubCategories.value = filteredSubCategories
    } else {
        retrieveUnfilteredNaturalSubCats()
    }

    Log.i(tag, "Filtered List Size: ${_foodSubCategories.value?.size}")

}

    fun retrieveUnfilteredNaturalSubCats() {
        _foodSubCategories.value = grocerySubCategories
    }

    fun filterFoodSubCategoriesByInput(userInput: String,context: Context) {
        viewModelScope.launch {
            if (userInput.isNotEmpty()) {
                val filteredFood = _foodSubCategories.value?.filter { food ->
                    context.getString(food.stringRessourceTitle).startsWith(userInput, ignoreCase = true)
                        ?: false
                }

                if (filteredFood != null) {
                    _foodSubCategories.value = filteredFood
                    Log.e("Filter", "Filtered food list: $filteredFood")
                }
            } else {
                retrieveFilteredNaturalSubCats(context,userInput)
            }
        }
    }

    fun retrieveFilteredNaturalSubCats(context: Context, text : String) {
        _foodSubCategories.value = grocerySubCategories.filter { context.getString(it.parentCategory) == text  }
    }


    fun sortFoodSubCategoriesByAlphabet(sort: Boolean,context: Context) {
        viewModelScope.launch {
            val filteredExercises = _foodSubCategories.value
            val sortedExercises = if (sort) {
                filteredExercises?.sortedByDescending { context.getString(it.stringRessourceTitle) }
            } else {
                filteredExercises?.sortedBy { context.getString(it.stringRessourceTitle) }
            }
            _foodSubCategories.value = sortedExercises
        }
    }

}