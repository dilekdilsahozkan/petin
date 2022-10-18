package com.moralabs.pet.message.data.remote.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ChatDto (
    val to: UserDto? = null,
    val messages: List<MessageDto>? = null,
    val unreadMessages: Int? = null,
    val isUserBlocked: Boolean? = null,
    val pageIndex: Int? = null
) : BaseDto(), Parcelable