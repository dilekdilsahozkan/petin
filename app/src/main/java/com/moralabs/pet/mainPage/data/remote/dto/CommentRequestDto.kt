package com.moralabs.pet.mainPage.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class CommentRequestDto(
    val text: String? = null
): BaseDto()