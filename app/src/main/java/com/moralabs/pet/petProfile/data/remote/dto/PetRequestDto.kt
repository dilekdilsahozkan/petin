package com.moralabs.pet.petProfile.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto

data class PetRequestDto (
    val name: String? = null,
    var media: List<MediaDto>? = null,
    val petAttributes: List<PetPostAttributeDto>? = null
): BaseDto()

data class PetPostAttributeDto(
    val attributeId: String? = "",
    val attributeChoiceId: String? = null,
    val choice: String? = null
) : BaseDto()