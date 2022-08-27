package com.moralabs.pet.newPost.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.MediaDto
import com.moralabs.pet.core.domain.BaseDto

data class NewPostDto (
    val media: List<MediaDto>? = null,
    val text : String? = null,
    val type: Int? = null,
    val location: List<LocationDto>? = null,
    val petId: String? = null
):BaseDto()