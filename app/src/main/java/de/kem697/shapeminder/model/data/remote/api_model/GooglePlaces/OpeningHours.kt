package de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class OpeningHours(
    @Json(name="open_now")
    val openNow: Boolean?
)
