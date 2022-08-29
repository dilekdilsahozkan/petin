package com.moralabs.pet.settings.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class SettingsRequestDto (
    val refreshToken: String
): BaseDto()