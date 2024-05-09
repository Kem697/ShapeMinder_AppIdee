package model.data.local.model.myTraining

import kotlinx.serialization.Serializable


@Serializable
data class Exercise(
    val stringRessourceTitle: Int,
    val stringRessourceText: Int,
    val imageRessource: Int,
    val isExercise: Boolean,
    var isInExerciseList: Boolean,
    val bodyPart: Int,
    var isSaved: Boolean,
    val video: Int?,
    var addedToSession: Boolean?
)




