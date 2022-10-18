package com.moralabs.pet.settings.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class SettingsRequestDto (
    val refreshToken: String
): BaseDto()