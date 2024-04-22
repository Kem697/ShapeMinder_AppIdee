package model.data.local

import androidx.room.Dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import model.data.local.model.TrainingsSession

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
}