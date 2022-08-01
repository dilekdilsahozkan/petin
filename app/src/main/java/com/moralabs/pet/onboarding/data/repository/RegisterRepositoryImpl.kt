package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.onboarding.data.remote.api.RegisterService
import com.moralabs.pet.onboarding.data.remote.dto.RegisterDto
import com.moralabs.pet.onboarding.data.remote.dto.RegisterRequestDto
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val service: RegisterService)
    : RegisterRepository , BaseRepository{
    override suspend fun register(registerPet: RegisterRequestDto): Response<BaseResponse<RegisterDto>> = service.register(registerPet)
}