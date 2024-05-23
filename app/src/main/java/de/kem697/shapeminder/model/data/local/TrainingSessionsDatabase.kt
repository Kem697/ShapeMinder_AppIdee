package de.kem697.shapeminder.model.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSession
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSessionConv


@Database(entities = [TrainingsSession::class], version = 1)
@TypeConverters(TrainingsSessionConv::class)
abstract class TrainingSessionsDatabase : RoomDatabase(){

    abstract val trainingsSessionDao: TrainingSessionsDataDao

}
@Volatile
private lateinit var INSTANCE: TrainingSessionsDatabase


fun getTrainingDatabase(context: Context) : TrainingSessionsDatabase {



    synchronized(TrainingSessionsDatabase::class.java){
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TrainingSessionsDatabase::class.java,
                "database_training"
            )
                /*Using .fallbackToDestructiveMigration() tells Room to perform a
                destructive migration if it encounters a version mismatch
                (i.e., if the current database version doesn't match the version expected by the app).*/
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}