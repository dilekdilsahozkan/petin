package com.moralabs.pet.profile.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class UserDto(
    val id: String = "",
    val image: String? = null,
    val userName: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val birthDate: String? = null,
    val followerCount: Int? = null,
    val followedCount: Int? = null,
    val postCount: Int? = null,
    val pageIndex: Int? = null
) : BaseDto()
