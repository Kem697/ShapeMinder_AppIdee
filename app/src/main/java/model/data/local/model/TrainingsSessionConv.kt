package model.data.local.model

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class TrainingsSessionConv {
    @TypeConverter
    fun toList(contents: String): MutableList<Content> {
        return decodeFromString<MutableList<Content>>(contents)
    }


    @TypeConverter
    fun fromList(trainingsSessions : MutableList<Content>): String {
        return Json.encodeToString(trainingsSessions)
    }

}