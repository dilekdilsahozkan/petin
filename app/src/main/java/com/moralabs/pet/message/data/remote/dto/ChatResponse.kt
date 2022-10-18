package com.moralabs.pet.message.data.remote.dto

import androidx.annotation.Keep

@Keep
data class ChatResponse(
    val success: Boolean = false,
    val code: String? = null,
    val message: String? = null,
    val userMessage: String? = null
)
