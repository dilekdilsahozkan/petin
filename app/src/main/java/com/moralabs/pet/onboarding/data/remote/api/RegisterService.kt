package com.moralabs.pet.onboarding.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("/auth/register")
    suspend fun register(
        @Body registerPet: RegisterRequestDto
    ): Response<BaseResponse<RegisterDto>>
}