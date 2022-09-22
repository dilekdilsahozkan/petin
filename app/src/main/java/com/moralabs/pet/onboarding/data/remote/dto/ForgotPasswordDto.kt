package com.moralabs.pet.onboarding.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class ForgotPasswordDto(
    val email: String? = null,
    val codeType: Int? = null
): BaseDto()

data class PasswordCodeDto(
    val code: String? = null
): BaseDto()

data class NewPasswordDto(
    var email: String? = null,
    var code: String? = null,
    val newPassword: String? = null
): BaseDto()
