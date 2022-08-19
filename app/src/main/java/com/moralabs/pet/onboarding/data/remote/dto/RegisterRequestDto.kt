package com.moralabs.pet.onboarding.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class RegisterRequestDto (
    val fullName: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val agreement:List<Any>? = null,
): BaseDto()
