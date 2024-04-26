package model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Nutriments(
val energy: Double = 0.0,
@Json(name = "energy-kcal")
val calories: Double = 0.0,
val fat: Double = 0.0,

@Json(name ="saturated-fat")
val saturatedFat: Double = 0.0,

val carbohydrates: Double = 0.0,
val sugars: Double = 0.0,
val proteins: Double = 0.0,
val salt: Double = 0.0
)
