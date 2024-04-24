package model.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.data.remote.api_model.FoodCategories
import model.data.remote.api_model.FoodResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


const val BASE_URL = "https://platform.fatsecret.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()




interface FatSecretApi{
    @POST("rest/server.api")
    suspend fun exampleFoodDetails(
        @Header("Authorization") authToken: String,
        @Header("Content-Type") contentType:String = "application/json",
        /*method muss mit: "food_categories.get.v2" initialisiert werden.*/
        @Query("method") method: String = "food.get.v2",


        @Query("food_id") foodId: Int,
//        @Query("food_name") foodName:String,
        /*format muss mit: "json" initialisiert werden.*/
        @Query("format") format: String ="json",

    ): FoodResult



  /*  @POST("rest/server.api")
    suspend fun getFoodCategories(
        @Query("method") method: String,
        @Query("region") region: String,
        @Query("calories") calories: String,
        @Query("protein") protein: String,
        @Query("fat") fat: String,
        @Query("carbohydrate") carbohydrate: String): Food

}
*/


@POST("rest/server.api")
@FormUrlEncoded
suspend fun getFoodCategoriesById(
    @Header("Authorization") authToken: String,
    @Field("method") method: String,
    @Field("region") region: String,
    @Query("format") format: String ="json"): FoodCategories

}



object FoodApi{
    val retrofitService: FatSecretApi by lazy { retrofit.create(FatSecretApi::class.java) }
}