package model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.data.remote.api_model.token.AccessToken

@Database(entities = [AccessToken::class], version = 1)
abstract class FatSecretDatabase : RoomDatabase(){

    abstract val fatSecretTokenDao: FatSecretDataDao

}

private lateinit var INSTANCE: FatSecretDatabase


fun getDatabase(context: Context) : FatSecretDatabase {


    //Überprüfe ob die Datenbank bereits existiert
    if (!::INSTANCE.isInitialized) {

        //Wenn die Datenbank noch nicht existiert dann erstelle sie
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            FatSecretDatabase::class.java,
            "database_token"
        ).build()
    }

    //In jedem Fall liefer die Instanz der Datenbank zurück
    return INSTANCE
}
