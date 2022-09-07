package com.moralabs.pet.mainPage.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class CommentRequestDto(
    val text: String? = null
): BaseDto()