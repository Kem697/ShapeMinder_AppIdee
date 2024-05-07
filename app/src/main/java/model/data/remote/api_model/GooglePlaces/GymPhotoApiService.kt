package model.data.remote.api_model.GooglePlaces






import android.icu.text.ListFormatter.Width
import androidx.browser.trusted.sharing.ShareTarget.EncodingType
import com.example.shapeminder_appidee.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val Photo_BASE_URL = "https://maps.googleapis.com"

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
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Photo_BASE_URL)
    .client(client)
    .build()


private val key = BuildConfig.apiKey

interface GymPhotoApi {

    @GET("/maps/api/place/photo")
    suspend fun getGymPlacePhoto(
//        @Query("maxWidth") maxWidth: Int = 300,
        @Query("maxheight") maxHeight: Int = 500,
        @Query("photo_reference") photoReference: String,
        @Query("key") apiKey: String = "$key",
    ): String

}





object GymLocationApi{
    val retrofitService: GymPhotoApi by lazy { retrofit.create(GymPhotoApi::class.java) }
}

