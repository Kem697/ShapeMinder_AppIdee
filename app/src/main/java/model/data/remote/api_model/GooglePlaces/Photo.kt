package model.data.remote.api_model.GooglePlaces

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val height: Int,
    @Json(name="html_attributions")

    val attributions: List<String> = listOf(),

    @Json(name = "photo_reference")
    val reference: String,

    val width: Int
)
