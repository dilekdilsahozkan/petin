package com.moralabs.pet.newPost.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.LocationDto
import com.moralabs.pet.core.data.remote.dto.BaseDto
import java.io.File

data class NewPostDto (
    val media: List<MediaDto>? = null,
    val text : String? = null,
    val type: Int? = null,
    val location: LocationDto? = null,
    val petId: String? = null,
    var files: List<File>? = null
): BaseDto()

data class MediaDto(
    val id: String? = null,
    val url: String? = null
) : BaseDto()