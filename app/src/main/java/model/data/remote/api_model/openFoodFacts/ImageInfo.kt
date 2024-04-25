package model.data.remote.api_model.openFoodFacts

import kotlinx.serialization.Serializable


@Serializable
data class ImageInfo(
val small: String,
val thumb: String,
val full: String

)
