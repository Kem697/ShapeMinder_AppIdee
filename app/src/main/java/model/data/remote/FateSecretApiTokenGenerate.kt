package model.data.remote


import com.example.shapeminder_appidee.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.data.AccessToken
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.POST


val GENERATE_TOKEN_URL = "https://oauth.fatsecret.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GENERATE_TOKEN_URL)
    .build()

private var clientId = BuildConfig.clientId
private var clientSecret = BuildConfig.clientSecret


interface FatSecretApiToken {
    @POST("connect/token")
    suspend fun generateToken(
        @Header("Authorization") auth: String = "$clientId:$clientSecret",
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("scope") scope: String = "basic",
    ): AccessToken

}

object FoodTokenApi {
    val retrofitService: FatSecretApiToken by lazy { retrofit.create(FatSecretApiToken::class.java) }
}

