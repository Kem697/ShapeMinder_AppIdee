package model.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.data.FoodResponse
import model.data.FoodResult
import okhttp3.ResponseBody
import org.intellij.lang.annotations.Language
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.security.Signature
import java.security.Timestamp
import java.text.DecimalFormat

const val BASE_URL = "https://platform.fatsecret.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface FatSecretApi{
    @POST("rest/server.api")
    fun exampleFoodDetails(
        @Header("Authorization") authToken: String,
        @Header("Content-Type") contentType:String = "application/json",
        /*method muss mit: "food_categories.get.v2" initialisiert werden.*/


//        @Query("method") method: String = "food_categories.get.v2",
//        @Query("food_id") foodId: Int,
//        /*format muss mit: "json" initialisiert werden.*/
//        @Query("format") format: String ="json",

        /*region sollte "DE" für Deutschland sein.*/
        /*@Query("region") region: String,
        @Query("language") language: String,
        @Query("calories") calories: DecimalFormat,
        @Query("protein") protein: DecimalFormat,
        @Query("fat") fat: DecimalFormat,
        @Query("carbohydrate") carbohydrate: DecimalFormat*/
    ): FoodResult
}

object FoodApi{
    val retrofitService: FatSecretApi by lazy { retrofit.create(FatSecretApi::class.java) }
}