package com.moralabs.pet.core.data.remote.dto

import com.moralabs.pet.newPost.data.remote.dto.MediaDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
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
    val pageIndex: Int? = null,
    var selected: Boolean = false
): BaseDto()

data class ContentDto(
    val text: String? = null,
    val media: List<MediaDto>? = null,
    val pet: PetDto? = null,
    val type: Int? = null,  // 0 -> POST, 1 -> QNA, 2 -> FIND PARTNER, 3 -> ADOPTION
    val location: LocationDto? = null
): BaseDto()

data class LocationDto(
    val latitude: Float? = null,
    val longitude: Float? = null,
    val city: String? = null,
    val district: String? = null
): BaseDto()

data class CommentDto(
    val id: String = "",
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val content: ContentDto? = null,
    val comments: List<CommentsDto>? = null,
    val likeCount: Int? = null,
    val isCommentLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()

data class CommentsDto(
    val id: String = "",
    val user: UserInfoDto? = null,
    val dateTime: Long? = null,
    val text: String? = null,
    val likeCount: Int? = null,
    val isCommentLikedByUser: Boolean? = null,
    val pageIndex: Int? = null
): BaseDto()