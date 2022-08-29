package com.moralabs.pet.petProfile.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class PetDto(
    val id: String? = null,
    val name: String? = null,
    val media: List<String>? = null,
    val petAttributes: List<PetAttributeDto>? = null,
    val pageIndex: Int? = null
) : BaseDto()

data class PetAttributeDto(
    val attributeId: String? = null,
    val attributeName: String? =null,
    val choice: String? = null
) : BaseDto()