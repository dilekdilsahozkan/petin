package com.moralabs.pet.settings.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class SettingsRequestDto (
    val refreshToken: String
): BaseDto()