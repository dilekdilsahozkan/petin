package com.moralabs.pet.core.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.RefreshTokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshTokenDto: RefreshTokenDto): Response<BaseResponse<LoginDto>>
}