package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>>
}