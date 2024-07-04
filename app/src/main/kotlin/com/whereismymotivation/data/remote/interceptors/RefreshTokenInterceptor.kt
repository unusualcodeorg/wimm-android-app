package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.apis.auth.RefreshTokenApi
import com.whereismymotivation.data.remote.apis.auth.request.RefreshTokenRequest
import com.whereismymotivation.data.remote.utils.ForcedLogout
import com.whereismymotivation.di.qualifier.AccessTokenInfo
import com.whereismymotivation.di.qualifier.RefreshTokenInfo
import com.whereismymotivation.utils.common.ResultCallbackBlocking
import com.whereismymotivation.utils.common.ResultFetcherBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenInterceptor @Inject constructor(
    @AccessTokenInfo private val accessTokenFetcher: ResultFetcherBlocking<String>,
    @AccessTokenInfo private val accessTokenCallback: ResultCallbackBlocking<String>,
    @RefreshTokenInfo private val refreshTokenFetcher: ResultFetcherBlocking<String>,
    @RefreshTokenInfo private val refreshTokenCallback: ResultCallbackBlocking<String>,
    private val refreshTokenApi: RefreshTokenApi,
    private val forcedLogout: ForcedLogout,
) : Interceptor {

    companion object {
        private const val INSTRUCTION = "instruction"
        private const val REFRESH_ACCESS_TOKEN = "refresh_token"
        private const val LOGOUT = "logout"
        private val LOCK = Object()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val response = chain.proceed(builder.build())

        if (!response.isSuccessful && response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val instruction = response.header(INSTRUCTION)

            if (instruction != null) {
                if (instruction == LOGOUT) {
                    forcedLogout.logout()
                } else if (instruction == REFRESH_ACCESS_TOKEN) {
                    val previousAccessToken = accessTokenFetcher.fetch()

                    synchronized(LOCK) {
                        val newAccessToken = accessTokenFetcher.fetch()
                        if (previousAccessToken != null && newAccessToken != null && previousAccessToken == newAccessToken) {
                            val refreshToken = refreshTokenFetcher.fetch()
                            if (refreshToken != null) {

                                val refreshTokenCall =
                                    refreshTokenApi.refreshToken(RefreshTokenRequest(refreshToken))
                                        .execute()

                                if (refreshTokenCall.isSuccessful) {
                                    val tokenResponse = refreshTokenCall.body()
                                    if (tokenResponse != null) {
                                        accessTokenCallback.onResult(tokenResponse.data.accessToken)
                                        refreshTokenCallback.onResult(tokenResponse.data.refreshToken)
                                    }
                                } else {
                                    forcedLogout.logout()
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
        }

        return response
    }

}