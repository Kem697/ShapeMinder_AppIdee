package model.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity
@TypeConverters(TrainingsSessionConv::class)
data class TrainingsSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var sessionName : String = "",
    var sessionDate : String = "",
    var trainingsSession: List<Content>
)