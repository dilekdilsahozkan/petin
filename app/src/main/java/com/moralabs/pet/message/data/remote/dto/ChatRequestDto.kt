package com.moralabs.pet.message.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class ChatRequestDto (
    val text: String? = null
): BaseDto()