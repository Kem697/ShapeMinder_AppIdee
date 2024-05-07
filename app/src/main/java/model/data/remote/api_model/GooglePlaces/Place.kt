package model.data.remote.api_model.GooglePlaces

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable


@Serializable

data class Place(
    val name: String,


    @Json(name="formatted_address")
    val adress: String,

    @Json(name = "rating")
    val averageRating: Double,

    @Json(name="user_ratings_total")
    val totalRatings: Int,

    @Json(name="opening_hours")
    val openend: OpeningHours,

    val photos: List<Photo> = listOf()
)
