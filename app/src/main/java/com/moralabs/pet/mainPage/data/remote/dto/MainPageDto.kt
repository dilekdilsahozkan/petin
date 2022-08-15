package com.moralabs.pet.mainPage.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class MainPageDto (
    val id: String? = null,
    val userId: String? = null,
    val dateTime: String? = null,
    val content: String? = null,
    val likeCount: Int? = null,
    val commentCount: Int? = null,
    val type: Int? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()