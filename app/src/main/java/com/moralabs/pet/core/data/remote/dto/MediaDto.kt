package com.moralabs.pet.core.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class MediaDto(
    val id: String? = null,
    val url: String? = null
) : BaseDto()