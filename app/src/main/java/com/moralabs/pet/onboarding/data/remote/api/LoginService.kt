package com.moralabs.pet.onboarding.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.ForgotPasswordDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginDto
import com.moralabs.pet.onboarding.data.remote.dto.LoginRequestDto
import com.moralabs.pet.onboarding.data.remote.dto.NewPasswordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    suspend fun login(@Body loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>>

    @POST("/auth/email")
    suspend fun forgotPassword(@Body sendEmail: ForgotPasswordDto): Response<BaseResponse<Nothing>>

    @PATCH("/auth/auth/email/password")
    suspend fun newPassword(@Body getCode: NewPasswordDto): Response<BaseResponse<Nothing>>
}