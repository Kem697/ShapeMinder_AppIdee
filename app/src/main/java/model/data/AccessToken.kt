package model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity
data class AccessToken(
    @PrimaryKey (autoGenerate = false)
    val id: Long = 0,
    @Json(name = "access_token")
    val accessToken: String,

    /*Standarddatum*/
    val time: String = "2024-01-01T00:00:00"
)

