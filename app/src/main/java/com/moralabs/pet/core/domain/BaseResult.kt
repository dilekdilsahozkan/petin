package com.moralabs.pet.core.domain

sealed class BaseResult<out T : Any> {
    data class Success <T: Any>(val data : T) : BaseResult<T>()
    data class Error(val error : ErrorResult) : BaseResult<Nothing>()
}

data class ErrorResult (
    val code: ErrorCode,
    val message: String? = null
)

enum class ErrorCode {
    SERVER_ERROR,
    NO_DATA,
    EMPTY_USERNAME,
    EMPTY_EMAIL
}
