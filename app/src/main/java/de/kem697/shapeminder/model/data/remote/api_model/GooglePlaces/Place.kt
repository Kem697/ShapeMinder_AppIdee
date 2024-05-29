package de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces

import com.google.android.libraries.places.api.model.PhotoMetadata
import com.squareup.moshi.Json
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable



data class Place(
    val name: String,

    @Json(name="formatted_address")
    val address: String,

    @Json(name = "rating")
    val averageRating: Double,

    @Json(name="user_ratings_total")
    val totalRatings: Int,

    val reference : String,

    @Json(name="opening_hours")
    val currentlyOpen: OpeningHours?,

    @Contextual
    val photos: List<Photo> = listOf(),

    @Json(name="place_id")
    val id : String
)
