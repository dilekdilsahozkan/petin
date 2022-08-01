package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import retrofit2.Response

interface RegisterRepository {
    suspend fun register(registerPet: RegisterRequestDto): Response<BaseResponse<RegisterDto>>
}