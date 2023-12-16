package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.request.RefreshTokenRequest
import com.whereismymotivation.di.AccessTokenInfo
import com.whereismymotivation.di.RefreshTokenInfo
import com.whereismymotivation.utils.common.ResultCallback
import com.whereismymotivation.utils.common.ResultFetcher
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenInterceptor @Inject constructor(
    @AccessTokenInfo private val accessTokenFetcher: ResultFetcher<String>,
    @AccessTokenInfo private val accessTokenCallback: ResultCallback<String>,
    @RefreshTokenInfo private val refreshTokenFetcher: ResultFetcher<String>,
    @RefreshTokenInfo private val refreshTokenCallback: ResultCallback<String>
) : Interceptor {

    companion object {
        private const val INSTRUCTION = "instruction"
        private const val REFRESH_ACCESS_TOKEN = "refresh_token"
        private val LOCK = Object()
    }

    private lateinit var networkService: NetworkService

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val response = chain.proceed(builder.build())

        if (!response.isSuccessful && response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val instruction = response.header(INSTRUCTION)

            if (instruction != null && instruction == REFRESH_ACCESS_TOKEN) {
                val previousAccessToken = accessTokenFetcher.fetch()

                synchronized(LOCK) {
                    val newAccessToken = accessTokenFetcher.fetch()
                    if (previousAccessToken != null && newAccessToken != null && previousAccessToken == newAccessToken) {
                        val refreshToken = refreshTokenFetcher.fetch()
                        if (refreshToken != null) {

                            val refreshTokenCall =
                                networkService.refreshToken(RefreshTokenRequest(refreshToken))
                                    .execute()

                            if (refreshTokenCall.isSuccessful) {
                                val tokenResponse = refreshTokenCall.body()
                                if (tokenResponse != null) {
                                    accessTokenCallback.onResult(tokenResponse.data.accessToken)
                                    refreshTokenCallback.onResult(tokenResponse.data.refreshToken)
                                }
                            }
                        }
                    }

                    val accessToken = accessTokenFetcher.fetch()

                    if (accessToken != null) {
                        builder.removeHeader(RequestHeaders.Param.ACCESS_TOKEN.value)
                        builder.addHeader(
                            RequestHeaders.Param.ACCESS_TOKEN.value,
                            "Bearer $accessToken"
                        )
                    }

                    response.body?.close()

                    return chain.proceed(builder.build())
                }

            }
        }

        return response
    }

}