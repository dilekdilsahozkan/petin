package com.moralabs.pet.mainPage.data.remote.dto

sealed class ContentTypeDto {
    data class NormalPostDto(
        val media: List<String>? = null,
        val text: String? = null
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