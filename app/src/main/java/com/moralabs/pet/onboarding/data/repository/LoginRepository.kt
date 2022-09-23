package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.*
import retrofit2.Response

interface LoginRepository {
    suspend fun guestLogin(): Response<BaseResponse<LoginDto>>
    suspend fun login(loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>>
    suspend fun forgotPassword(sendEmail: ForgotPasswordDto): Response<BaseResponse<Nothing>>
    suspend fun passwordCode(passwordCode: PasswordCodeDto): Response<BaseResponse<Nothing>>
    suspend fun newPassword(newPw: NewPasswordDto): Response<BaseResponse<Nothing>>
}