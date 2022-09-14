package com.moralabs.pet.message.presentation.model

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class UiChatMessageDto(
    val text: String? = null,
    val type: UiChatMessageType,
    val hour: String? = null
) : BaseDto()

enum class UiChatMessageType{
    TITLE,
    INCOMING_MESSAGE,
    OUTGOING_MESSAGE
}