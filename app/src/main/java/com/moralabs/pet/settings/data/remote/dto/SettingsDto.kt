package com.moralabs.pet.settings.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

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

data class EditUserDto(
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: String? = null,
) : BaseDto()