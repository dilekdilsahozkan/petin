package com.moralabs.pet.offer.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class OfferRequestDto(
    val postId: String? = null,
    val petId: String? = null,
    val text: String? = null
) : BaseDto()