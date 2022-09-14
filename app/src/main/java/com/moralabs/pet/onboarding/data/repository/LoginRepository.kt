package com.moralabs.pet.onboarding.data.repository

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.ForgotPasswordDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import retrofit2.Response

interface LoginRepository {
    suspend fun login(loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>>
    suspend fun forgotPassword(sendEmail: ForgotPasswordDto): Response<BaseResponse<Nothing>>
    suspend fun newPassword(getCode: NewPasswordDto): Response<BaseResponse<Nothing>>
}