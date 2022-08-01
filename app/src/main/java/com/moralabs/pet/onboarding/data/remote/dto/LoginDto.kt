package com.moralabs.pet.onboarding.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class LoginDto (
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val accessTokenExpiration: String? = null,
    val refreshTokenExpiration: String? = null,
    val tokenType: String? = null
): BaseDto()