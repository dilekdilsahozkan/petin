package com.moralabs.pet.mainPage.data.remote.dto

sealed class ContentDto {
    data class PostDto(
        val media: List<String>? = null,
        val text: String? = null
    ):ContentDto()

    data class QNADto(
        val image: Int? =null,
    ):ContentDto()

    data class FindPartnerDto(
        val image: Int? =null,
    ):ContentDto()

    data class AdoptionDto(
        val image: Int? =null,
    ):ContentDto()
}