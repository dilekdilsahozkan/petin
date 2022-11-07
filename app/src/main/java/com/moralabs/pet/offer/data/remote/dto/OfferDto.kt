package com.moralabs.pet.offer.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class OfferDto(
    val id: String = "",
    val pet: PetDto? = null,
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val text: String? = null,
    val pageIndex: Int? = null,
    val status: Int? = null
): BaseDto(), Parcelable

@Keep
data class OfferDetailDto(
    val otherPet: PetDto? = null,
    val readOffer: OfferDto? = null,
    val allOffers: List<OfferDto>? = null
)