package model.data.remote


import android.util.Base64
import com.example.shapeminder_appidee.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.data.remote.api_model.token.AccessToken
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


const val GENERATE_TOKEN_URL = "https://oauth.fatsecret.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GENERATE_TOKEN_URL)
    .build()

private var clientId = BuildConfig.clientId
private var clientSecret = BuildConfig.clientSecret



val authHeader = "Basic " + Base64.encodeToString("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)


interface FatSecretApiToken {

    @POST("connect/token")
    @FormUrlEncoded
    suspend fun generateToken(
        @Header("Authorization") auth: String = "$authHeader",
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("scope") scope: String = "basic",
    ): AccessToken

//content-type     application/x-www-form-urlencoded
//
}

object FoodTokenApi {
    val retrofitService: FatSecretApiToken by lazy { retrofit.create(FatSecretApiToken::class.java) }
}







