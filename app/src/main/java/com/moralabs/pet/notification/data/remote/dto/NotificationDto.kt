package com.moralabs.pet.notification.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto
import com.moralabs.pet.newPost.data.remote.dto.MediaDto

data class NotificationDto(
    val userMedia: MediaDto? = null,
    val text: String? = null,
    val type: Int? = null,
    val contentId: String? = null,
    val dateTime: Long? = null,
    val pageIndex: Int? = null,
    val isRead: Boolean? = false
) : BaseDto()