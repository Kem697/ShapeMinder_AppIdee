package model.data.remote.api_model

data class Food(
    val food_id: String,
    val food_name: String,
    val food_type: String,
    val food_url: String,
    val servings: Servings
)
