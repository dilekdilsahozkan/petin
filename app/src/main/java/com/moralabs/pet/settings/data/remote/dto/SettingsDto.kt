package com.moralabs.pet.settings.data.remote.dto

import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto

@Keep
data class BlockedDto(
    val userId: String? = null,
    val fullName: String? = null,
    val userName: String? = null,
    val media: Media? = null,
    var selected: Boolean = false
) : BaseDto()

@Keep
data class Media(
    val id: String? = null,
    val url: String? = null
) : BaseDto()

@Keep
data class EditUserDto(
    var media: List<MediaDto>? = null,
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: String? = null,
) : BaseDto()

@Keep
data class ChangePasswordRequestDto(
    val oldPassword: String? = null,
    val newPassword: String? = null,
)