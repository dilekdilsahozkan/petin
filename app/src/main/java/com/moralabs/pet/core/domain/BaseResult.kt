package com.moralabs.pet.core.domain

sealed class BaseResult<out T : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T>()
    data class Error(val error: ErrorResult) : BaseResult<Nothing>()
}

data class ErrorResult(
    val code: ErrorCode,
    val message: String? = null,
    val serverCode: Int? = null
)

enum class ErrorCode {
    SERVER_ERROR,
    NO_DATA,
    EMPTY_BLANKS,
    PASSWORD_LENGTH_LESS_THAN_EIGHT,
    USERNAME_VALID,
    EMAIL_VALID,
    AUTH_WRONG_EMAIL_OR_PASSWORD,
    EMPTY_NEW_POST_TEXT
}
