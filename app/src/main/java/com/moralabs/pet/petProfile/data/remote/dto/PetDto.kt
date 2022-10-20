package com.moralabs.pet.petProfile.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PetDto(
    val id: String? = "",
    val name: String? = null,
    val media: MediaDto? = null,
    val petAttributes: List<PetAttributeDto>? = null,
    val pageIndex: Int? = null,
    var selected: Boolean = false
) : BaseDto(), Parcelable

@Keep
@Parcelize
data class PetAttributeDto(
    val attributeId: String? = "",
    val attributeChoiceId: String? = null,
    val attributeName: String? = null,
    val attributeType: Int? = null,
    val type: Int? = null,
    val choice: String? = null,
) : BaseDto(), Parcelable

@Keep
@Parcelize
data class AttributeDto(
    val id: String? = "",
    val attributeType : Int? = null,
    val type: Int? = null,
    val parentAttributeChoiceId: String? = null,
    val name: String? = null,
    val isRequired: Boolean = false,
    val isEditable: Boolean = false,
    val choices: List<ChoicesDto>? = null,
) : BaseDto(), Parcelable

@Keep
@Parcelize
data class ChoicesDto(
    val id: String? = "",
    val choice: String? = null,
) : BaseDto(), Parcelable

@Keep
data class CreatePostDto(
    val getValue: List<PetDto>? = null,
    val postValue: List<PetDto>? = null
)
@Keep
data class CreateOfferDto(
    val getOffer: List<PetDto>? = null,
    val offerValue: List<PetDto>? = null
)