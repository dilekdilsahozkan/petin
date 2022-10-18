package com.moralabs.pet.onboarding.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null,
): BaseDto()
