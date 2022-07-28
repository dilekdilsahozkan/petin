package com.moralabs.pet.onboarding.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null,
):BaseDto()
