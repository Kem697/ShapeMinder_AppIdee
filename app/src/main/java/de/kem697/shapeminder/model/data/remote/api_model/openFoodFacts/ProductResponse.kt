package de.kem697.shapeminder.model.data.remote.api_model.openFoodFacts

import com.squareup.moshi.Json

data class ProductResponse(

    val products : List<Product> = listOf()
)
