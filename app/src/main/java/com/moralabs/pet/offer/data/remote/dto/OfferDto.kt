package com.moralabs.pet.offer.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class OfferDto(
    val postId: String? = null,
    val petId: String? = null,
    val text: String? = null
) : BaseDto()