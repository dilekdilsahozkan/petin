package com.moralabs.pet.onboarding.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import retrofit2.Response
import retrofit2.http.GET

interface WelcomeService {
    @GET("test")
    suspend fun getWelcomePage() : Response<BaseResponse<WelcomeDto>>
}