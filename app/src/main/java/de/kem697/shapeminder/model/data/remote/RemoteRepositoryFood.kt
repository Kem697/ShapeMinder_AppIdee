package de.kem697.shapeminder.model.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.kem697.shapeminder.R
import de.kem697.shapeminder.model.data.local.ProductDatabase
import de.kem697.shapeminder.model.data.local.model.myNutrion.FoodFinderCategory
import de.kem697.shapeminder.model.data.local.model.myNutrion.FoodFinderSubCategory
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.OpenFoodFactsApi
import de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts.Product

import kotlin.Exception

class RemoteRepositoryFood (
    private val openFoodApi: OpenFoodFactsApi,
    private val productDatabase: ProductDatabase
)

{
    var groceryCategories = loadGroceryCategories()
    var grocerySubCategories = loadGrocerySubCategories()

    val savedFoodList : LiveData<List<Product>> = productDatabase.productDao.getAllProduct()


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



    private var _scannedFood = MutableLiveData<Product>()

    val scannedFood : MutableLiveData<Product>

        get() = _scannedFood



    suspend fun searchFoodByBarcode(barcode: String){
        try {
            val result = openFoodApi.retrofitService.searchFoodByBarcode(barcode)
            _scannedFood.postValue(result.product)
        } catch (e:Exception){
            var tag ="Barcode Scan??"
            Log.i(tag,"Fehler bei der API Anfrage!: $e")
        }
    }

    fun loadGroceryCategories(): List<FoodFinderCategory>{
        return listOf(
            FoodFinderCategory(
                R.string.parentCat_Carbs,
                R.drawable.food_main_cats_carbs,"Getreide",true),
            FoodFinderCategory(
                R.string.parentCat_Fats,
                R.drawable.food_main_cats_fats,"Obst und Gemüse",true),
            FoodFinderCategory(
                R.string.parentCat_Protein,
                R.drawable.food_main_cats_protein,"Molkerei und Eier",true),
            FoodFinderCategory(
                R.string.parentCat_Fibers,
                R.drawable.food_main_cats_fibers,"Öle und Fette",true))
    }



    fun loadGrocerySubCategories() : List<FoodFinderSubCategory>{
        return listOf(
            FoodFinderSubCategory(R.string.gc_noodles,R.drawable.carbs_subcat_pastas,R.string.parentCat_Carbs,R.string.apiSearchTag_pastas),
            FoodFinderSubCategory(R.string.gc_bread_bread_products,R.drawable.carbs_subcat_bread,R.string.parentCat_Carbs,R.string.apiSearchTag_bread),
            FoodFinderSubCategory(R.string.gc_rice_rice_products,R.drawable.carbs_subcat_rice,R.string.parentCat_Carbs,R.string.apiSearchTag_rice),
            FoodFinderSubCategory(R.string.gc_cereals_cereals_products,R.drawable.carbs_subcat_cereals,R.string.parentCat_Carbs,R.string.apiSearchTag_cereals),

            FoodFinderSubCategory(R.string.gc_meatproducts,R.drawable.foodcat3_meat_img,R.string.parentCat_Protein,R.string.apiSearchTag_meat),
            FoodFinderSubCategory(R.string.gc_fishproducts,R.drawable.proteins_subcat_fish,R.string.parentCat_Protein,R.string.apiSearchTag_fish),
            FoodFinderSubCategory(R.string.gc_dairy_and_dairy_products,R.drawable.proteins_subcat_dairys,R.string.parentCat_Protein,R.string.apiSearchTag_dairy),
            FoodFinderSubCategory(R.string.gc_cheese_products,R.drawable.proteins_subcat_cheese,R.string.parentCat_Protein,R.string.apiSearchTag_cheese),

            FoodFinderSubCategory(R.string.gc_fruits_and_vegetable,R.drawable.foodcat4_fruits_and_vegetables,R.string.parentCat_Fibers,R.string.apiSearchTag_fruits),
            FoodFinderSubCategory(R.string.gc_beans,R.drawable.fibers_subcat_beans,R.string.parentCat_Fibers,R.string.apiSearchTag_beans),
            FoodFinderSubCategory(R.string.gc_seeds,R.drawable.fibers_subcat_seeds,R.string.parentCat_Fibers,R.string.apiSearchTag_seeds),
            FoodFinderSubCategory(R.string.gc_greens_greens_products,R.drawable.fibers_subcat_vegetables,R.string.parentCat_Fibers,R.string.apiSearchTag_vegetables),

            FoodFinderSubCategory(R.string.gc_oils,R.drawable.foodcat2_oil_img,R.string.parentCat_Fats,R.string.apiSearchTag_olive_oils),
            FoodFinderSubCategory(R.string.gc_nuts,R.drawable.fats_subcat_nuts,R.string.parentCat_Fats,R.string.apiSearchTag_nuts),
        )
    }


    suspend fun insertProduct (product: Product){
        try {
            productDatabase.productDao.insertProduct(product)
        } catch (e:Exception){
            var tag = "Eintrag in Produktdatenbank"
            Log.i(tag,"Fehler bei der Speicherung des Produtkts in die DB: $e")
        }
    }


    suspend fun deleteProduct (product: Product){
        try {
            productDatabase.productDao.deleteProduct(product)
        } catch (e:Exception){
            var tag = "Austrag in Produktdatenbank"
            Log.i(tag,"Fehler bei der Entfernung des Produtkts in der DB: $e")
        }
    }


}