package com.moralabs.pet.message.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class ChatRequestDto (
    val text: String? = null
): BaseDto()