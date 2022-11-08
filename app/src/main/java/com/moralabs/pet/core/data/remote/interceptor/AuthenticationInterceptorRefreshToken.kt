package com.moralabs.pet.core.data.remote.interceptor

import com.moralabs.pet.BuildConfig
import com.moralabs.pet.core.data.remote.api.AuthenticationService
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.onboarding.data.remote.dto.RefreshTokenDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthenticationInterceptorRefreshToken(
    private val userRepository: AuthenticationRepository,
) : Interceptor {

    companion object {
        private var refreshing = false
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val initialResponse = chain.proceed(originalRequest)

        when {
            ((initialResponse.code == 403 || initialResponse.code == 401) &&
                    listOf(
                        userRepository.getAuthentication()?.refreshKey,
                        userRepository.getAuthentication()?.bearerKey
                    ).all { it != null }) -> {
                if (refreshing) {

                    return runBlocking {
                        val oldBearer = userRepository.getAuthentication()?.bearerKey

                        while (oldBearer == userRepository.getAuthentication()?.bearerKey) {
                            delay(100)
                        }

                        userRepository.getAuthentication()?.bearerKey?.let {
                            val newAuthenticationRequest = originalRequest.newBuilder()
                                .removeHeader("Authorization")
                                .addHeader("Authorization", "Bearer $it").build()
                            chain.proceed(newAuthenticationRequest)
                        } ?: run {
                            initialResponse
                        }
                    }
                } else {
                    refreshing = true

                    return runBlocking {
                        val response =
                            getService().refreshToken(RefreshTokenDto(userRepository.getAuthentication()?.refreshKey))

                        var refreshingResponse = when {
                            (response == null || response.code() != 200) -> {
                                initialResponse
                            }
                            else -> {
                                if( listOf(
                                            response.body()?.data?.accessToken,
                                            response.body()?.data?.refreshToken
                                        ).any { it == null }) {
                                    initialResponse
                                } else {
                                    userRepository.refreshLogin(
                                        response.body()?.data?.accessToken,
                                        response.body()?.data?.refreshToken
                                    )

                                    val newAuthenticationRequest = originalRequest.newBuilder()
                                        .removeHeader("Authorization")
                                        .addHeader("Authorization", "Bearer ${response.body()?.data?.accessToken}").build()
                                    chain.proceed(newAuthenticationRequest)
                                }

                            }
                        }

                        refreshing = false
                        refreshingResponse
                    }
                }
            }
            else -> return initialResponse
        }

    }

    fun getService(): AuthenticationService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.PET_BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthenticationService::class.java)

    }
}