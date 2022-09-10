package com.moralabs.pet.profile.data.remote.dto

import android.os.Parcelable
import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDto(
    val userId: String = "",
    val media: MediaDto? = null,
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: Long? = null,
    val followerCount: Int? = null,
    val followedCount: Int? = null,
    val postCount: Int? = null,
    val pageIndex: Int? = null
) : BaseDto(), Parcelable
