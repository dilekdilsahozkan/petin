package com.moralabs.pet.message.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto
import com.moralabs.pet.profile.data.remote.dto.UserDto

data class ChatDto (
    val to: List<UserDto>? = null,
    val messages: List<MessageDto>? = null,
    val unreadMessages: Int? = null,
    val pageIndex: Int? = null
) : BaseDto()