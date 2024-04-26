package model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable


@Serializable
data class Product(
    @Json(name = "product_name")
    val productName: String?,

    val nutriments: Nutriments?,

    @Json(name="image_small_url")
    val url : String,

    @Json(name ="image_urls")
    val imageUrls: ImageUrls?


)






