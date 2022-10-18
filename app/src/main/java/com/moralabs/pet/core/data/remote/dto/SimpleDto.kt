package com.moralabs.pet.core.data.remote.dto

import androidx.annotation.Keep

@Keep
data class SimpleDto<T>(val value: T) : BaseDto()