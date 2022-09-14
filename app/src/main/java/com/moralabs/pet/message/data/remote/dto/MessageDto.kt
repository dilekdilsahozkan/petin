package com.moralabs.pet.message.data.remote.dto

import android.os.Parcelable
import com.moralabs.pet.core.data.remote.dto.BaseDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageDto(
    val id: String?,
    val text: String? = null,
    val dateTime: Long? = null,
    val username: String? = null,
    val isUser: Boolean? = null,
    val pageIndex: Int? = null
) : BaseDto(), Parcelable
