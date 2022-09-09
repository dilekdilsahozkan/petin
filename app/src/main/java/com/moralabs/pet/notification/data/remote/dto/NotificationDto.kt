package com.moralabs.pet.notification.data.remote.dto

import com.moralabs.pet.core.data.remote.dto.BaseDto

data class GroupNotification(
    val notification: List<NotificationDto>? = null
) : BaseDto()

data class NotificationDto(
    val text: String? = null,
    val type: Int? = null,
    val contentId: String? = null,
    val dateTime: Long? = null,
    val pageIndex: Int? = null
) : BaseDto()