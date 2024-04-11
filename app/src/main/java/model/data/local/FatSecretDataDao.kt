package model.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.data.remote.api_model.token.AccessToken

@Dao
interface FatSecretDataDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(token: AccessToken)

    @Query("SELECT * from accesstoken WHERE id = 0")
    fun getToken(): LiveData<AccessToken>

}
