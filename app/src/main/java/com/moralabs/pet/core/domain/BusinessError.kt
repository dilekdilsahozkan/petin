package com.moralabs.pet.core.domain

import androidx.annotation.Keep

@Keep
data class BusinessError(
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

