package de.kem697.shapeminder.model.data.local.model.myTraining

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TrainingsSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var sessionName : String = "",
    var sessionDate : String = "",
    var performance: MutableList<Performance>,
    var trainingsSession: MutableList<Exercise>
)
