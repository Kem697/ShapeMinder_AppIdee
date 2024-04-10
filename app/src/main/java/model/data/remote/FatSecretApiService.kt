package model.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.data.FoodResult
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.Signature
import java.security.Timestamp

const val BASE_URL = "https://platform.fatsecret.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface FatSecretApi{
    @GET("/rest/server.api?method=food.get.v3")
    suspend fun getFood(
        @Query("food_id")foodId :Int,
        @Query("oauth_consumer_key")consumerKey:String,
        @Query("oauth_nonce") nonce: String,
        @Query("oauth_signature")signature: String,
        @Query("oauth_signature_method") signatureMethod: String,
        @Query("oauth_timestamp") timestamp: String,
        @Query("oauth_version") version: String
    ): Call<ResponseBody>
}

object FoodApi{
    val retrofitService: FatSecretApi by lazy { retrofit.create(FatSecretApi::class.java) }
}