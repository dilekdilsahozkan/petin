package com.moralabs.pet.settings.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class SettingsDto (
    val success: Boolean = true,
    val code: String? = null,
    val message: String? = null,
    val userMessage: String? = null
): BaseDto()