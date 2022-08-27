package com.moralabs.pet.core.data.remote.interceptor

import com.moralabs.pet.core.data.repository.AuthenticationRepository
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val userRepository: AuthenticationRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Content-Type", "application/json")

        userRepository.requestHeaders().forEach {
            it.value?.let { value ->
                requestBuilder.header(it.key, value)
            }
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}