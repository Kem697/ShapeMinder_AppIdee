package model.data.local.model

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

class TrainingsSessionConv {
    @TypeConverter
    fun toList(contents: String): List<Content> {
        return decodeFromString<List<Content>>(contents)
    }


    @TypeConverter
    fun toContents(trainingsSessions : List<Content>): String {
        return Json.encodeToString(trainingsSessions)
    }
}