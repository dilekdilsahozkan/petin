package com.moralabs.pet.onboarding.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null,
): BaseDto()
