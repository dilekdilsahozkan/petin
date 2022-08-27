package com.moralabs.pet.mainPage.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.CommentDto
import com.moralabs.pet.core.data.remote.dto.ContentDto
import com.moralabs.pet.profile.data.remote.dto.UserInfoDto

sealed class ContentTypeDto {
    data class NormalPostDto(
        val user: List<UserInfoDto>? = null,
        val dateTime: String? = null,
        val content: List<ContentDto>? = null,
        val comments: List<CommentDto>? = null,
    ):ContentTypeDto()

    data class QNADto(
        val image: Int? =null,
    ):ContentTypeDto()

    data class FindPartnerDto(
        val image: Int? =null,
    ):ContentTypeDto()

    data class AdoptionDto(
        val image: Int? =null,
    ):ContentTypeDto()

}