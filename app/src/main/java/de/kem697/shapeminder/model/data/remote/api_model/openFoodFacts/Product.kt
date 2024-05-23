package de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable



@Entity
@Serializable
data class Product(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @Json(name = "product_name_de")
    val productNameDe: String?,

    @Json(name = "categories_tags")
    val categories: List<String>,

    val nutriments: Nutriments?,

    @Json(name="image_small_url")
    val url : String ="",

    @Json(name ="image_urls")
    val imageUrls: ImageUrls?,


    @Json(name ="stores_tags")
    val store: List<String>? = listOf(),

    var isSaved: Boolean = false


)






