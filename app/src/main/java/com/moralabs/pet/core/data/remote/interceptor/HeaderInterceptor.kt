package com.moralabs.pet.core.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            //.header("Authorization", userRepository.getUser().authToken ?: "")
            .header("Content-Type", "application/json")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}