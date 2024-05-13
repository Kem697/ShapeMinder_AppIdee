package model.data.remote.api_model.openFoodFacts

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProductConv {
    @TypeConverter
    fun toList(savedProduct: String): MutableList<String> {
        return Json.decodeFromString<MutableList<String>>(savedProduct)
    }

    @TypeConverter
    fun fromList(allSavedProducts: MutableList<String>): String {
        return Json.encodeToString(allSavedProducts)
    }


    @TypeConverter
    fun fromNutriments(productNutriments: Nutriments?): String? {
        return Json.encodeToString(productNutriments)
    }


    @TypeConverter
    fun toNutriments(productNutriments: String?): Nutriments? {
        return if (productNutriments != null){
            Json.decodeFromString(productNutriments)
        } else{
            null
        }
    }


    @TypeConverter
    fun fromImages(productImages: ImageUrls?): String? {
        return if (productImages!= null){
            Json.encodeToString(productImages)
        } else{
            null
        }
    }


    @TypeConverter
    fun toImages(productImages: String?): ImageUrls? {
        return if (productImages != null){
            Json.decodeFromString(productImages)
        } else{
            null
        }
    }
}