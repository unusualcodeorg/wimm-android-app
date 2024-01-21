package com.whereismymotivation.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.net.ssl.HttpsURLConnection

@JsonClass(generateAdapter = true)
data class ApiErrorResponse(
    val status: Status = Status.UNKNOWN,

    @Json(name = "statusCode")
    val statusCode: Int = -1,

    @Json(name = "message")
    val message: String = "Something went wrong"
) {
    @Keep
    enum class Status(val code: Int) {
        UNKNOWN(-100),
        REMOTE_CONNECTION_ERROR(-101),
        NETWORK_CONNECTION_ERROR(-102),
        HTTP_UNAUTHORIZED(HttpsURLConnection.HTTP_UNAUTHORIZED),
        HTTP_FORBIDDEN(HttpsURLConnection.HTTP_FORBIDDEN),
        HTTP_BAD_REQUEST(HttpsURLConnection.HTTP_BAD_REQUEST),
        HTTP_NOT_FOUND(HttpsURLConnection.HTTP_NOT_FOUND),
        HTTP_INTERNAL_ERROR(HttpsURLConnection.HTTP_INTERNAL_ERROR),
        HTTP_UNAVAILABLE(HttpsURLConnection.HTTP_UNAVAILABLE),
        HTTP_BAD_GATEWAY(HttpsURLConnection.HTTP_BAD_GATEWAY);

        companion object {
            private val codes = entries.toTypedArray()
            operator fun get(code: Int) = codes.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}
