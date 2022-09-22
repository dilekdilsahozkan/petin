package com.moralabs.pet.onboarding.data.remote.api

import com.moralabs.pet.core.data.remote.dto.BaseResponse
import com.moralabs.pet.onboarding.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    suspend fun login(@Body loginPet: LoginRequestDto): Response<BaseResponse<LoginDto>>

    @POST("/auth/email")
    suspend fun forgotPassword(@Body sendEmail: ForgotPasswordDto): Response<BaseResponse<Nothing>>

    @POST("/auth/code")
    suspend fun passwordCode(@Body passwordCode: PasswordCodeDto): Response<BaseResponse<Nothing>>

    @PATCH("/auth/email/password")
    suspend fun newPassword(@Body newPw: NewPasswordDto): Response<BaseResponse<Nothing>>
}