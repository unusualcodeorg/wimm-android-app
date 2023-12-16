package com.whereismymotivation.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val TAG = "NetworkHelper"
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return (actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
    }

    fun castToNetworkError(e: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {
            if (e is ConnectException) return NetworkError(0, "0")
            if (e !is HttpException) return defaultNetworkError
            val error = Moshi.Builder()
                .build()
                .adapter(NetworkError::class.java)
                .fromJson(e.response()?.errorBody()?.string().orEmpty())
            return error?.let {
                return@let NetworkError(e.code(), error.statusCode, error.message)
            } ?: defaultNetworkError
        } catch (e: IOException) {
            Logger.e(TAG, e.toString())
        } catch (e: JsonDataException) {
            Logger.e(TAG, e.toString())
        } catch (e: NullPointerException) {
            Logger.e(TAG, e.toString())
        }
        return defaultNetworkError
    }
}