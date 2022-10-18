package com.moralabs.pet.offer.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto

@Keep
data class OfferRequestDto(
    val postId: String? = null,
    val petId: String? = null,
    val text: String? = null
) : BaseDto()