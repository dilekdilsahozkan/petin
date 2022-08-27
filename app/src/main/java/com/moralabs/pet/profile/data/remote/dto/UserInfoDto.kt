package com.moralabs.pet.profile.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class UserInfoDto(
    val userId: String? = null,
    val userName: String? = null,
    val image: String? = null
) : BaseDto()
