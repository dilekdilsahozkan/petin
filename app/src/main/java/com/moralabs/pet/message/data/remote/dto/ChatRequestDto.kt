package com.moralabs.pet.message.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class ChatRequestDto (
    val text: String? = null
):BaseDto()