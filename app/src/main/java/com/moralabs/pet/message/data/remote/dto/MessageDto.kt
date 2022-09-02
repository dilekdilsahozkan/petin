package com.moralabs.pet.message.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class MessageDto(
    val id: String?,
    val text: String? = null,
    val dateTime: Long? = null,
    val username: String? = null,
    val isUser: Boolean? = null,
    val pageIndex: Int? = null
) : BaseDto()
