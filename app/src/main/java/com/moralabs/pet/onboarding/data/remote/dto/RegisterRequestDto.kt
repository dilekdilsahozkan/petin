package com.moralabs.pet.onboarding.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class RegisterRequestDto (
    val fullName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val agreement:List<Any>? = null,
): BaseDto()
