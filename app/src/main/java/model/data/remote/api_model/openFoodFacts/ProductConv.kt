package model.data.remote.api_model.openFoodFacts

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.data.local.model.myTraining.Exercise

class ProductConv {
    @TypeConverter
    fun toList(savedProduct: String): MutableList<Product> {
        return Json.decodeFromString<MutableList<Product>>(savedProduct)
    }

    @TypeConverter
    fun fromList(allSavedProducts: MutableList<Product>): String {
        return Json.encodeToString(allSavedProducts)
    }

}