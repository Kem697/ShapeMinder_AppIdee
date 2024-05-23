package de.kem697.shapeminder.model.data.local

import androidx.room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSession

@Dao
interface TrainingSessionsDataDao {


    @Query("SELECT * FROM TRAININGSSESSION")
    fun getAll(): LiveData<List<TrainingsSession>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(newTrainingsSession: TrainingsSession)

    @Update
    suspend fun updateSession(trainingsSession: TrainingsSession)


    @Delete
    suspend fun deleteSession(trainingsSession: TrainingsSession)

    /*    @Delete
        suspend fun deleteExerciseFromDb(exercise: Content)*/
}