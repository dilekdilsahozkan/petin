package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.onboarding.data.remote.api.WelcomeService
import com.moralabs.pet.onboarding.data.remote.dto.WelcomeDto
import retrofit2.Response
import javax.inject.Inject

class WelcomeRepositoryImpl @Inject constructor(private val service: WelcomeService) :
    WelcomeRepository, BaseRepository {
    override suspend fun getWelcomePage(): Response<BaseResponse<WelcomeDto>> =
        service.getWelcomePage()
}