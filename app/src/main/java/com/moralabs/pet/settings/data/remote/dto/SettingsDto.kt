package com.moralabs.pet.settings.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class SettingsDto(
    val success: Boolean = true,
    val code: String? = null,
    val message: String? = null,
    val userMessage: String? = null
) : BaseDto()

data class BlockedDto(
    val userId: String? = null,
    val fullName: String? = null,
    val userName: String? = null,
    val media: Media? = null,
    var selected: Boolean = false
) : BaseDto()

data class Media(
    val id: String? = null,
    val url: String? = null
) : BaseDto()