package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import retrofit2.Response

interface WelcomeRepository {
    suspend fun getWelcomePage(): Response<BaseResponse<WelcomeDto>>
}