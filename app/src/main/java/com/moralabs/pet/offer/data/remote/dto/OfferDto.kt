package com.moralabs.pet.offer.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto

@Keep
data class OfferDto(
    val id: String = "",
    val pet: PetDto? = null,
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val text: String? = null,
    val pageIndex: Int? = null,
    val status: Int? = null
): BaseDto()

@Keep
data class OfferDetailDto(
    val otherPet: PetDto? = null,
    val readOffer: OfferDto? = null,
    val allOffers: List<OfferDto>? = null
)