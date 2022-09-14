package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.onboarding.data.remote.api.LoginService
import com.moralabs.pet.onboarding.data.remote.dto.ForgotPasswordDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val service: LoginService)
    : LoginRepository, BaseRepository {
    override suspend fun login(loginPet: LoginRequestDto) = service.login(loginPet)
    override suspend fun forgotPassword(sendEmail: ForgotPasswordDto) = service.forgotPassword(sendEmail)
    override suspend fun newPassword(getCode: NewPasswordDto) = service.newPassword(getCode)
}