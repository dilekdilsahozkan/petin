package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.repository.BaseRepository
import com.moralabs.pet.onboarding.data.remote.api.LoginService
import com.moralabs.pet.onboarding.data.remote.dto.*
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val service: LoginService)
    : LoginRepository, BaseRepository {
    override suspend fun guestLogin() = service.guestLogin()
    override suspend fun login(loginPet: LoginRequestDto) = service.login(loginPet)
    override suspend fun externalLogin(external: ExternalLoginDto) = service.externalLogin(external)
    override suspend fun forgotPassword(sendEmail: ForgotPasswordDto) = service.forgotPassword(sendEmail)
    override suspend fun passwordCode(passwordCode: PasswordCodeDto) = service.passwordCode(passwordCode)
    override suspend fun newPassword(newPw: NewPasswordDto) = service.newPassword(newPw)
}