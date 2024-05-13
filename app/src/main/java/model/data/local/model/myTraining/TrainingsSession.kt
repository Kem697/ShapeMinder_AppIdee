package model.data.local.model.myTraining

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TrainingsSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var sessionName : String = "",
    var sessionDate : String = "",

    var trainingsSession: MutableList<Exercise>
)
