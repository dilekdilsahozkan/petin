package com.moralabs.pet.onboarding.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class LoginDto (
    val userId: String = "",
    val isExternalUser: Boolean? = false,
    val isGuestUser: Boolean? = false,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val accessTokenExpiration: String? = null,
    val refreshTokenExpiration: String? = null,
    val tokenType: String? = null
): BaseDto()

@Keep
data class ExternalLoginDto (
    val externalAuthToken: String,
    val userName: String,
    val type: Int? = null
): BaseDto()

enum class ExternalType(val type: Int){
    FACEBOOK(1),
    GOOGLE(2)
}