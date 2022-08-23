package com.moralabs.pet.core.data.remote.dto

data class BaseResponse<T>(
    val data: T? = null,
    val success: Boolean = false,
    val code: String? = null,
    val message: String? = null,
    val userMessage: String? = null
)