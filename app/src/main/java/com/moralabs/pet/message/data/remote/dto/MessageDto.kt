package com.moralabs.pet.message.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class MessageDto (
    val accessToken: String? = null,
    ) : BaseDto()