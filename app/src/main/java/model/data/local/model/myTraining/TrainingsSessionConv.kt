package model.data.local.model.myTraining

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import model.data.remote.api_model.openFoodFacts.ImageUrls

class TrainingsSessionConv {
    @TypeConverter
    fun toList(contents: String): MutableList<Exercise> {
        return decodeFromString<MutableList<Exercise>>(contents)
    }


    @TypeConverter
    fun fromList(trainingsSessions : MutableList<Exercise>): String {
        return Json.encodeToString(trainingsSessions)
    }


    @TypeConverter
    fun fromPerformance(trainingPerformance: MutableList<Performance>): String {
        return Json.encodeToString(trainingPerformance)
    }


    @TypeConverter
    fun toPerformance(trainingPerformance: String): MutableList<Performance> {
        return Json.decodeFromString<MutableList<Performance>>(trainingPerformance)
    }

}