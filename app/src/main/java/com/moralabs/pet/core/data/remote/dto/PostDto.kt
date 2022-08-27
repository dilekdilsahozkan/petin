package com.moralabs.pet.core.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto

data class PostDto(
    val id: String? = null,
    val user: List<UserInfoDto>? = null,
    val dateTime: String? = null,
    val content: List<ContentDto>? = null,
    val comments: List<CommentDto>? = null,
    val likeCount: Int? = null,
    val commentCount: Int? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()

data class ContentDto(
    val media: List<String>? = null,
    val text: String? = null,
    val type: Int? = null,
    val location: List<LocationDto>? = null
): BaseDto()

data class LocationDto(
    val latitude: Int? = null,
    val longitude: Int? = null
): BaseDto()

data class CommentDto(
    val id: String? = null,
    val user: List<UserInfoDto>? = null,
    val text: String? = null,
    val commentCount: Int? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()