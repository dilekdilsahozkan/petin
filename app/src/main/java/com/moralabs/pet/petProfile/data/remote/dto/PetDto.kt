package com.moralabs.pet.petProfile.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto

data class PetDto(
    val id: String? = "",
    val name: String? = null,
    val media: MediaDto? = null,
    val petAttributes: List<PetAttributeDto>? = null,
    val pageIndex: Int? = null,
    var selected: Boolean = false
) : BaseDto()

data class PetAttributeDto(
    val attributeId: String? = "",
    val attributeName: String? = null,
    val type: Int? = null,
    val choice: String? = null,
    var isLoading: Boolean = false

) : BaseDto()

data class CreatePostDto(
    val getValue: List<PetDto>? = null,
    val postValue: List<PetDto>? = null
)

data class CreateOfferDto(
    val getOffer: List<PetDto>? = null,
    val offerValue: List<PetDto>? = null
)

data class CreatePetDto(
    val getPet: PetDto? = null,
    val addPet: List<PetDto>? = null
)