package model.data.remote




import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import model.data.remote.api_model.openFoodFacts.ProductResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val FoodFact_BASE_URL = "https://world.openfoodfacts.net"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val loggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(FoodFact_BASE_URL)
    .client(client)
    .build()


interface FoodFactApi {
    @GET("/api/v2/search")
    suspend fun searchFood(
        @Query("categories_tags_en") foodCatEn: String,
        @Query("countries_tags_en") countryTagEn: String
    ): ProductResponse



    @GET("/api/v2/search{barcode}")
    suspend fun searchFoodByBarcode(
        @Path("barcode") barcode: String,
    ): ProductResponse
}





object OpenFoodFactsApi{
    val retrofitService: FoodFactApi by lazy { retrofit.create(FoodFactApi::class.java) }
}

