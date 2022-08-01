package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.onboarding.data.remote.api.LoginService
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val service: LoginService)
    : LoginRepository, BaseRepository {
    override suspend fun login(loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>> = service.login(loginPet)
}