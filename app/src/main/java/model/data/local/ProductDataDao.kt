/*
package model.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.data.remote.api_model.openFoodFacts.Product


@Dao
interface ProductDataDao {


    @Query("SELECT * FROM Product")
    fun getAllProduct(): LiveData<List<Product>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)
}*/
