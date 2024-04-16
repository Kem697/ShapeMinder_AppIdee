package model.data.local.model

data class Content(
    val stringRessourceTitle: Int,
    val stringRessourceText: Int,
    val imageRessource: Int,
    val isExercise: Boolean,
    var isInExerciseList: Boolean,
    val bodyPart: String,
    var isSaved: Boolean,
    val video: Int?,
    var addedToSession: Boolean?
)




