package model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Nutriments(
val energy: Int,
val fat: Int,
@Json(name ="saturated-fat")
val saturatedFat: Int,
val carbohydrates: Int,
val sugars: Int,
val proteins: Int,
val salt: Int
)
