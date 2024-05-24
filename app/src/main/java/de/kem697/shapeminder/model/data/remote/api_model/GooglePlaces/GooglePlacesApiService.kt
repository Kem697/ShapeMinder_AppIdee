package de.kem697.shapeminder.model.data.remote.api_model.GooglePlaces





import de.kem697.shapeminder.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val GooglePlaces_BASE_URL = "https://maps.googleapis.com"

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
    .baseUrl(GooglePlaces_BASE_URL)
    .client(client)
    .build()


private val key = BuildConfig.apiKey

interface PlacesApi {
    @GET("/maps/api/place/textsearch/json")
    suspend fun getGymPlace(
        @Query("query") input: String = "fitness",
        @Query("radius") radius: Int = 5000,
        @Query("key") apiKey: String = "$key",
    ): GooglePlacesResponse




}





object GooglePlacesApi{
    val retrofitService: PlacesApi by lazy { retrofit.create(PlacesApi::class.java) }
}
