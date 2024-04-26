package model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Nutriments(
val energy: Double,
val fat: Double,

@Json(name ="saturated-fat")
val saturatedFat: Double,

val carbohydrates: Double,
val sugars: Double,
val proteins: Double,
val salt: Double
)
