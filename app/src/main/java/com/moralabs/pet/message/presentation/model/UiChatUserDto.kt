package com.moralabs.pet.message.presentation.model

import android.os.Parcelable
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiChatUserDto(
    val user: UserDto?,
    var isSelected: Boolean = false
) : BaseDto(), Parcelable