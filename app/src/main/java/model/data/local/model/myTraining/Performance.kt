package model.data.local.model.myTraining

import kotlinx.serialization.Serializable


@Serializable
data class Performance(
    var sets: String = "",
    var reps: String = "",
    var weight: String = ""
)
