package de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json



data class ScannedFoodResponse(
    @Json(name = "code")
    val barcode: String = "",

    val product: Product
)
