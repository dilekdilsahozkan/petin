package com.moralabs.pet.offer.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto

data class OfferDto(
    val id: String = "",
    val pet: PetDto? = null,
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val text: String? = null,
    val pageIndex: Int? = null
): BaseDto()

data class OfferDetailDto(
    val readOffer: OfferDto? = null,
    val allOffers: List<OfferDto>? = null
)