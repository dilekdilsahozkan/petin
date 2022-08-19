package com.moralabs.pet.mainPage.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto

data class PostDto(
    val id: String? = null,
    val userId: String? = null,
    val dateTime: String? = null,
    val content: String? = null,
    val likeCount: Int? = null,
    val commentCount: List<ContentDto>? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
) : BaseDto()

data class ContentDto(
    val media: String? = null,
    val text: String? = null,
    val type: Int? = null,
    val location: List<LocationDto>? = null
)

data class LocationDto(
    val latitude: Int? = null,
    val longitude: Int? = null
)