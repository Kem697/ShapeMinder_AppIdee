package model.data.local.model.myTraining

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class TrainingsSessionConv {
    @TypeConverter
    fun toList(contents: String): MutableList<Exercise> {
        return decodeFromString<MutableList<Exercise>>(contents)
    }


    @TypeConverter
    fun fromList(trainingsSessions : MutableList<Exercise>): String {
        return Json.encodeToString(trainingsSessions)
    }

}