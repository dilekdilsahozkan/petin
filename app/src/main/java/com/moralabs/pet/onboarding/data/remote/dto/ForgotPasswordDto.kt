package com.moralabs.pet.onboarding.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class ForgotPasswordDto(
    val email: String? = null,
    val codeType: Int? = null
): BaseDto()

@Keep
data class PasswordCodeDto(
    val code: String? = null
): BaseDto()

@Keep
data class NewPasswordDto(
    var email: String? = null,
    var code: String? = null,
    val newPassword: String? = null
): BaseDto()

@Keep
data class RefreshTokenDto(
    var refreshToken: String? = null
): BaseDto()
