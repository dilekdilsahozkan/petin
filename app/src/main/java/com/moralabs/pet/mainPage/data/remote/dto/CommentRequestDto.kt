package com.moralabs.pet.mainPage.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class CommentRequestDto(
    val text: String? = null
):BaseDto()