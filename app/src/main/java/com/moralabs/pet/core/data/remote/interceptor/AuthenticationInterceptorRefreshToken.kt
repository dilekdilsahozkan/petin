package com.moralabs.pet.core.data.remote.interceptor

import com.moralabs.pet.BuildConfig
import com.moralabs.pet.core.data.remote.api.AuthenticationService
import com.moralabs.pet.core.data.repository.AuthenticationRepository
import com.moralabs.pet.onboarding.data.remote.dto.RefreshTokenDto
import dagger.Provides
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

class AuthenticationInterceptorRefreshToken(
    private val userRepository: AuthenticationRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val initialResponse = chain.proceed(originalRequest)

        when {
            initialResponse.code == 403 || initialResponse.code == 401 -> {

                return runBlocking {
                    val response = getService().refreshToken(RefreshTokenDto(userRepository.getAuthentication()?.refreshKey))

                    when {
                        response == null || response.code() != 200 -> {
                            initialResponse
                        }
                        else -> {
                            userRepository.refreshLogin(response.body()?.data?.accessToken, response.body()?.data?.refreshToken)

                            val newAuthenticationRequest = originalRequest.newBuilder()
                                .addHeader("Authorization", "Bearer " + response.body()?.data?.accessToken).build()
                            chain.proceed(newAuthenticationRequest)
                        }
                    }
                }
            }
            else -> return initialResponse
        }

    }

    fun getService(): AuthenticationService{
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