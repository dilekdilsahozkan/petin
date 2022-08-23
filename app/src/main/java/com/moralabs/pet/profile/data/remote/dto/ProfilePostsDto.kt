package com.moralabs.pet.profile.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class ProfilePostsDto (
    val type: Int,
    var username: String,
    var location: String,
    var postText: String
): BaseDto()