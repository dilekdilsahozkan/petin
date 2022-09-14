package com.moralabs.pet.petProfile.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto

data class PetRequestDto (
    val name: String? = null,
    val media: List<MediaDto>? = null,
    val petAttributes: List<PetAttributeDto>? = null
): BaseDto()