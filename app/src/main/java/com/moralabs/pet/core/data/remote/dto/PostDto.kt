package com.moralabs.pet.core.data.remote.dto

import com.moralabs.pet.core.domain.BaseDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto

data class PostDto(
    val id: String = "",
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val content: ContentDto? = null,
    val comments: List<CommentDto>? = null,
    val likeCount: Int? = null,
    val commentCount: Int? = null,
    val offerCount: Int? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()

data class ContentDto(
    val media: List<String>? = null,
    val text: String? = null,
    val pet: PetDto? = null,
    val type: Int? = null,
    val location: LocationDto? = null
): BaseDto()

data class LocationDto(
    val latitude: Int? = null,
    val longitude: Int? = null
): BaseDto()

data class CommentDto(
    val id: String = "",
    val user: List<UserInfoDto>? = null,
    val text: String? = null,
    val commentCount: Int? = null,
    val isPostLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()

data class CreateCommentDto(
    val commentValue: List<CommentDto>? = null,
    val userCommentValue: List<CommentDto>? = null
):BaseDto()