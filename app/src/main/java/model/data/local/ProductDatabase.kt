package model.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import model.data.remote.api_model.openFoodFacts.Product
import model.data.remote.api_model.openFoodFacts.ProductConv

@Database(entities = [Product::class], version = 1)
@TypeConverters(ProductConv::class)
abstract class ProductDatabase : RoomDatabase(){

    abstract val productDao: ProductDataDao

}
@Volatile
private lateinit var INSTANCE: ProductDatabase


fun getProductDatabase(context: Context) : ProductDatabase {


    synchronized(ProductDatabase::class.java){
        if (!::INSTANCE.isInitialized) {

            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java,
                "database_products"
            ).build()
        }
        return INSTANCE
    }
}
